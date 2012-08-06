package com.redhat.sforce.qb.model.chatter;

import java.io.Serializable;
import java.util.List;

public class Comments implements Serializable {

	private static final long serialVersionUID = 6187369115959941707L;
	private List<Comment> comments;
	private String type;
	private String url;
	private User user;
	private String currentPageUrl;
	private String nextPageUrl;
	private Long total;

}
