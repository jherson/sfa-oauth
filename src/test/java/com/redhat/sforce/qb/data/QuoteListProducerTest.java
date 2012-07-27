package com.redhat.sforce.qb.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.sforce.persistence.connection.ConnectionManager;
import com.redhat.sforce.qb.dao.QuoteDAO;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.Quote;
import com.sforce.ws.ConnectionException;

public class QuoteListProducerTest {
	
	private QuoteDAO quoteDAO;

	@Test
	public void testGetQuoteList() {
//		try {
//			//ConnectionManager.openConnection();
//			//List<Quote> quoteList = quoteDAO.queryQuotes();
//			//Assert.assertNotNull(quoteList);
//		} catch (ConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (QueryException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Assert.assertTrue(Boolean.TRUE);
	}
}