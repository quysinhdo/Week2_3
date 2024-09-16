package vn.iotstar.services.impl;

import vn.iotstar.dao.IUserDao;

import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.models.UserModel;
import vn.iotstar.services.IUserService;

public class UserServiceImpl implements IUserService {
	IUserDao userDao = new UserDaoImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.FindByUserName(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public UserModel FindByUserName(String Username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(Username);
	}

	@Override
	public UserModel register(String username, String password,String images, String fullname, String email, String phone) {
		if (userDao.findByUserName(username) != null) {
	        System.out.println("User đã tồn tại!");
	        return null;
	    }
		UserModel user = new UserModel(username, password,images,fullname,email,phone);
		userDao.insert(user);
		return user;
	}

}