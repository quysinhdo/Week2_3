package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.IUserService;
import vn.iotstar.services.impl.UserServiceImpl;
import vn.iotstar.ultis.Constant;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	IUserService service = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("username") != null) {
			resp.sendRedirect(req.getContextPath() + "/admin");
			return;
		}

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					session = req.getSession(true);
					session.setAttribute("username", cookie.getValue());
					resp.sendRedirect(req.getContextPath() + "/admin");
					return;
				}

			}
			req.getRequestDispatcher(Constant.REGISTER).forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");

		String username = req.getParameter("uname");
		String password = req.getParameter("pwd");
		String images = req.getParameter("images");
		String fullname = req.getParameter("fname");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		String alert = "";

		String btnresult = req.getParameter("action");
		if("login".equals(btnresult)) {
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		}

		if (username.isEmpty() || password.isEmpty() || images.isEmpty() || fullname.isEmpty() || email.isEmpty()
				|| phone.isEmpty()) {
			alert = "Thông tin đăng kí không được rỗng";
			req.setAttribute("alert", alert);
			req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
			return;
		}

		if ("register".equals(btnresult)) {
			UserModel user = service.register(username, password, images, fullname, email, phone);

			if (user != null) {
				alert = "Đăng kí tài khoản thành công";
				req.setAttribute("alert", alert);
				req.getRequestDispatcher("view/login.jsp").forward(req, resp);
			} else {
				alert = "Đăng kí tài khoản thất bại";
				req.setAttribute("alert", alert);
				req.getRequestDispatcher("/view/register.jsp").forward(req, resp);
			}
		}
	}
}