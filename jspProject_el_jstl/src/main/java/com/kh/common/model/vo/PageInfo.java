package com.kh.common.model.vo; // ㅡ 값을 담는 그릇(가방)이라고 생각, vo는 뭔가를 담으려고 만드는 것!!

public class PageInfo {

	private int listCount;		// 현재 총 게시글 개수
	private int currentPage;	// 현재 페이지 (즉, 사용자가 요청한 페이지)
	private int pageLimit;		// 페이지 하단에 보여질 페이징바의 최대 개수(몇개 단위씩 보여질건지)
	private int boardLimit;		// 한 페이지 내에 보여질 게시글 최대 개수(몇개 단위씩)
	private int maxPage;		// 가장 마지막 페이지 (총 페이지 수)
	private int startPage;		// 페이징바의 시작수
	private int endPage;		// 페이징바의 끝수
	
	public PageInfo() {}

	public PageInfo(int listCount, int currentPage, int pageLimit, int boardLimit, int maxPage, int startPage,
			int endPage) {
		super();
		this.listCount = listCount;
		this.currentPage = currentPage;
		this.pageLimit = pageLimit;
		this.boardLimit = boardLimit;
		this.maxPage = maxPage;
		this.startPage = startPage;
		this.endPage = endPage;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public int getBoardLimit() {
		return boardLimit;
	}

	public void setBoardLimit(int boardLimit) {
		this.boardLimit = boardLimit;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PageInfo [listCount=" + listCount + ", currentPage=" + currentPage + ", pageLimit=" + pageLimit
				+ ", boardLimit=" + boardLimit + ", maxPage=" + maxPage + ", startPage=" + startPage + ", endPage="
				+ endPage + "]";
	}
	
	
}
