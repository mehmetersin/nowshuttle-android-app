package com.mesoft.mobile.android.nowshuttle.om;

public class TransferStatus {

	private User user;
	private String state;

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

	public String getState() {

		if (state.equals("1")) {
			return "(Var)";
		} else {
			return "(Yok)";
		}
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toString() {
		return "-> "+user.getDisplayName() + "  " + getState();
	}

}
