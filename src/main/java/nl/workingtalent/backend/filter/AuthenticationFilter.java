//package nl.workingtalent.backend.filter;
//
//import java.io.IOException;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import nl.workingtalent.backend.entity.Account;
//import nl.workingtalent.backend.service.AccountService;
//
//@Component
//public class AuthenticationFilter extends OncePerRequestFilter {
//
//	@Autowired
//	private AccountService accountService;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		System.out.println("In filter");
//
//		// Lees header uit
//		String authHeader = request.getHeader("Authorization");
//		// Als header ingevuld is 
//		if (authHeader != null && !authHeader.isBlank()) {
//			// Vind account met die token
//			Optional<Account> optional = accountService.findByToken(authHeader);
//			// ALs de account gevonden
//			if (optional.isPresent()) {
//				System.out.println("Account is gevonden met naam " + optional.get().getEmail());
//
//				// Plaats de account in de request attribute
//				request.setAttribute("WT_ACCOUNT", optional.get());
//			}
//		}
//	}
//
//}