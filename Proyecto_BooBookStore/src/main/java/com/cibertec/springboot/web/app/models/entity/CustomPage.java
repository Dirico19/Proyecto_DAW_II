package com.cibertec.springboot.web.app.models.entity;

import java.util.List;

public class CustomPage<T> {

	private List<Libro> content;
	private CustomPageable pageable;
	private int totalElements;
	
	public class CustomPageable {
		private int pageNumber;
		private int pageSize;
		
		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
	}

	public List<Libro> getContent() {
		return content;
	}

	public void setContent(List<Libro> content) {
		this.content = content;
	}
	
	public CustomPageable getPageable() {
		return pageable;
	}

	public void setPageable(CustomPageable pageable) {
		this.pageable = pageable;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

}
