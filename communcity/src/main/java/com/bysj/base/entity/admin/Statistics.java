package com.bysj.base.entity.admin;

import java.util.List;

public class Statistics {
	private List<String> categories;
	private List<Integer> data;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Statistics{" +
				"categories=" + categories +
				", data=" + data +
				'}';
	}
}