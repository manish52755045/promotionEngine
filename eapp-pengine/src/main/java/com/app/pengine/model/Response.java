package com.app.pengine.model;
import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T reponseObj;
	private List<T> responseList;

	public T getReponseObj() {
		return reponseObj;
	}

	public void setReponseObj(T reponseObj) {
		this.reponseObj = reponseObj;
	}

	public List<T> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<T> responseList) {
		this.responseList = responseList;
	}

}

