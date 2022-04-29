package br.leandro.sp.guia.projetoguia.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.leandro.sp.guia.projetoguia.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)

			throws Exception {
		// variavel para obter a URI
		String uri = request.getRequestURI();
		// variavel para a sessão
		HttpSession session = request.getSession();
		// se for pagina de erro, libera
		if (uri.startsWith("/error")) {
			return true;
		}

		// verifica de handler é um handlerMethod
		// o que indica que ele está chamando um metodo em algum controller
		if (handler instanceof HandlerMethod) {
			// casting de Object para HandletMethod
			HandlerMethod metodo = (HandlerMethod) handler;
			if (uri.startsWith("/api")) {
				return true;
			} else {
				// verifia se este metodo é publico
				if (metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				// verifica se existe um usuário logado
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				// redireciona para pagina inicial
				response.sendRedirect("/");
				return false;
			}
		}

		return true;
	}
}
