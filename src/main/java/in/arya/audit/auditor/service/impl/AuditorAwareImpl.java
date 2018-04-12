package in.arya.audit.auditor.service.impl;

import in.arya.audit.auditor.service.AuditorAware;
import in.arya.audit.model.User;
import in.arya.audit.util.CommonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;


@Service
public class AuditorAwareImpl implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			OAuth2Authentication auth = (OAuth2Authentication) authentication;

			if (auth != null) {
				return CommonUtil.getOauthUser(auth);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}
}
