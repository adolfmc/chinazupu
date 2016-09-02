package org.springside.examples.quickstart.util;

import org.springside.examples.quickstart.entity.Clan;
import org.springside.examples.quickstart.entity.User;

public class StaticContainer {
	public static ThreadLocal<User> loginUser = new ThreadLocal<User>();
	public static ThreadLocal<Clan> loginClan = new ThreadLocal<Clan>();
}
