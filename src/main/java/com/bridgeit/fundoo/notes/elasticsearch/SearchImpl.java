package com.bridgeit.fundoo.notes.elasticsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.fundoo.notes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchImpl implements ISearch {

	private static final String INDEX = "notes";

	private static final String TYPE = "fundoo_notes_data";

	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	public SearchImpl(RestHighLevelClient client, ObjectMapper objectMapper) {
		this.client = client;
		this.objectMapper = objectMapper;
	}

	@Override
	public String createNote(Note note) throws Exception {

		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);

		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getNoteid()))
				.source(documentMapper);

		client.index(indexRequest, RequestOptions.DEFAULT);

		return "successful";
	}

	@Override
	public String updateNote(Note note) throws Exception {

		Note resultDocument = findById(note.getNoteid());

		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getNoteid()));

		Map<String, Object> noteMapper = objectMapper.convertValue(note, Map.class);

		updateRequest.doc(noteMapper);

		UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);

		return "updated";

	}

	public Note findById(long noteId) throws Exception {

		GetRequest getRequest = new GetRequest(INDEX, TYPE, String.valueOf(noteId));

		GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> resultMap = getResponse.getSource();

		return objectMapper.convertValue(resultMap, Note.class);

	}

	@Override
	public List<Note> findAll() throws Exception {

		SearchRequest searchRequest = new SearchRequest();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		return getSearchResult(searchResponse);

	}

	private List<Note> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();

		List<Note> note = new ArrayList<>();

		if (searchHit.length > 0) {

			Arrays.stream(searchHit)
					.forEach(hit -> note.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}

		return note;
	}

	@Override
	public String deleteNote(long noteId) throws Exception {

		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(noteId));
		DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);

		return "deleted";
	}

}
