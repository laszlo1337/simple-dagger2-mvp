
package com.example.leszek.simpledagger2mvp.domain.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total_count",
        "incomplete_results",
        "items"
})
public class UserSearchResult {

    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("incomplete_results")
    private boolean incompleteResults;
    @JsonProperty("items")
    private List<User> items = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserSearchResult() {
    }

    /**
     *
     * @param items
     * @param totalCount
     * @param incompleteResults
     */
    public UserSearchResult(int totalCount, boolean incompleteResults, List<User> items) {
        super();
        this.totalCount = totalCount;
        this.incompleteResults = incompleteResults;
        this.items = items;
    }

    @JsonProperty("total_count")
    public int getTotalCount() {
        return totalCount;
    }

    @JsonProperty("total_count")
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("incomplete_results")
    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    @JsonProperty("incomplete_results")
    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    @JsonProperty("items")
    public List<User> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<User> items) {
        this.items = items;
    }

}