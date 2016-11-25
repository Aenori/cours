package co.simplon.reserve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import co.simplon.reserve.facebook.FacebookUtil;
import co.simplon.reserve.facebook.OAuthServiceProvider;
import co.simplon.reserve.model.User;
import co.simplon.reserve.service.UserService;

@RequestMapping(value = "/social/facebook")
@Component
public class FacebookController<FacebookApi> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FacebookController.class);
	private static final String FACEBOOK = "facebook";
	private static final String PUBLISH_SUCCESS = "success";
	private static final String PUBLISH_ERROR = "error";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryRegistry;

	@Autowired
	private OAuth2Parameters oAuth2Parameters;

	@Autowired
	FacebookUtil facebookUtil;

	@Autowired
    private UserService userService;
	
	@Autowired
	@Qualifier("facebookServiceProvider")
	private OAuthServiceProvider<FacebookApi> facebookServiceProvider;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		FacebookConnectionFactory facebookConnectionFactory = (FacebookConnectionFactory) connectionFactoryRegistry
				.getConnectionFactory(FACEBOOK);
		OAuth2Operations oauthOperations = facebookConnectionFactory
				.getOAuthOperations();
		oAuth2Parameters.setState("recivedfromfacebooktoken");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		RedirectView redirectView = new RedirectView(authorizeUrl, true, true,
				true);

		return new ModelAndView(redirectView);
	}

	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView login(@RequestParam("code") String code,
			@RequestParam("state") String state, HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes attribute) throws Exception {

		OAuthService oAuthService = facebookServiceProvider.getService();

		Verifier verifier = new Verifier(code);
		Token accessToken = oAuthService
				.getAccessToken(Token.empty(), verifier);

		FacebookTemplate template = new FacebookTemplate(accessToken.getToken());

		FacebookProfile facebookProfile = template.userOperations().getUserProfile();
		String userId = facebookProfile.getId();
		Long fbId = Long.parseLong(userId);
		User user = userService.getByFacebookId( fbId );
		if( user == null )
		{
			ModelAndView model = new ModelAndView("uploadPage");
		    model.addObject("msg", "ERROR : no user in db with id " + fbId);
			return model;
		}

		Authentication auth = new UsernamePasswordAuthenticationToken (user.getName (),user.getPassword (),user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		ModelAndView model = new ModelAndView("redirect:/");
		model.addObject("successmsg", "Logged in User : " + user.getName() );
	    return model;
	}

	@RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
	@ResponseBody
	public void error(@RequestParam("error_reason") String errorReason,
			@RequestParam("error") String error,
			@RequestParam("error_description") String description,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			LOGGER.error(
					"Error occurred while validating user, reason is : {}",
					errorReason);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, description);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
