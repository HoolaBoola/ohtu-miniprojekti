/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendation_library;

import recommendation_library.dao.InMemoryRecommendationDao;
import recommendation_library.dao.RecommendationDao;
import recommendation_library.io.StubIO;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import recommendation_library.domain.BookRecommendation;

/**
 * @author jenni.makinen
 */
public class Stepdefs {

    final String separator = System.lineSeparator();

    List<String> inputLines;
    StubIO io;
    UserInterface ui;
    RecommendationDao dao;

    @Before
    public void setup() {
        this.inputLines = new ArrayList<String>();
    }

    @Given("command add is selected")
    public void commandAddSelected() {
        inputLines.add("1");
    }

    @When("command list is selected")
    public void commandListSelected() {
        inputLines.add("2");
    }

    @When("command delete is selected")
    public void commandDeleteSelected() {
        inputLines.add("4");
    }

    @When("command edit is selected")
    public void commandEditIsSelected() {
        inputLines.add("3");
    }

    @When("command book is selected")
    public void commandBookSelected() {
        inputLines.add("1");
    }

    @When("command search tags is selected")
    public void commandSearchTagsSelected() {
        inputLines.add("10");
    }

    @When("command list all is selected")
    public void commandAllListingSelected() {
        inputLines.add("1");
    }

    @When("command list books is selected")
    public void commandBookListingSelected() {
        inputLines.add("2");
    }

    @When("command list videos is selected")
    public void commandVideosListingSelected() {
        inputLines.add("3");
    }

    @When("command list blogs is selected")
    public void commandBlogsListingSelected() {
        inputLines.add("4");
    }

    @When("command list podcasts is selected")
    public void commandPodcastsListingSelected() {
        inputLines.add("5");
    }

    @When("command video is selected")
    public void commandVideoSelected() {
        inputLines.add("2");
    }

    @When("command blog is selected")
    public void commandBlogSelected() {
        inputLines.add("3");
    }

    @When("command podcast is selected")
    public void commandPodcastSelected() {
        inputLines.add("4");
    }

    @Given("blog recommendation with title {string} is added")
    public void blogRecommendationSuccessfullyadded(String title) {
        commandAddSelected();
        commandBlogSelected();
        addBlogRecommendation(title, "author", "description", "www.url.fi");
    }

    @Given("podcast recommendation with title {string} is added")
    public void podcastRecommendationSuccessfullyAdded(String title) {
        commandAddSelected();
        commandPodcastSelected();
        addPodcastRecommendation(title, "name", "author", "description");
    }

    @When("video recommendation with title {string}, timestamp {string} and comment {string} is added")
    public void addVideoWithTimestamp(String title, String timestamp, String comment) {
        inputLines.add(title);
        inputLines.add("default description");
        inputLines.add("default url");
        inputLines.add("0");
        inputLines.add("y");
        inputLines.add(timestamp);
        inputLines.add(comment);
        inputLines.add("n");
    }

    @When("book recommendation with author {string}, title {string}, description {string}, isbn {string} and page count {string} is added")
    public void addBookRecommendation(String author, String title, String description, String isbn, String pageCount) {
        inputLines.add(author);
        inputLines.add(title);
        inputLines.add(description);
        inputLines.add(isbn);
        inputLines.add(pageCount);
        inputLines.add("0");
    }

    @When("video recommendation with title {string}, description {string} and url {string} is added")
    public void addVideoRecommendation(String title, String description, String url) {
        inputLines.add(title);
        inputLines.add(description);
        inputLines.add(url);
        inputLines.add("0");
        inputLines.add("n");
    }

    @When("blog recommendation with title {string}, author {string}, description {string} and url {string} is added")
    public void addBlogRecommendation(String title, String author, String description, String url) {
        inputLines.add(title);
        inputLines.add(author);
        inputLines.add(description);
        inputLines.add(url);
        inputLines.add("0");
    }

    @When("podcast recommendation with title {string}, name {string}, author {string} and description {string} is added")
    public void addPodcastRecommendation(String title, String name, String author, String description) {
        inputLines.add(title);
        inputLines.add(name);
        inputLines.add(author);
        inputLines.add(description);
        inputLines.add("0");
    }

    @When("title {string} is entered")
    public void bookTitleIsEntered(String title) {
        inputLines.add(title);
    }

    @When("tag {string} is entered")
    public void tagIsEntered(String title) {
        inputLines.add(title);
    }

    @When("field to edit {string} is entered")
    public void fieldToEditIsEntered(String fieldToEdit) {
        inputLines.add(fieldToEdit);
    }

    @When("new value {string} for selected field is entered")
    public void newValueToEditableFieldIsEntered(String newValue) {
        inputLines.add(newValue);
    }

    @Then("value of the selected field {string} has been changed to {string}")
    public void editingBookWorks(String fieldName, String newValue) {
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        assertTrue(io.getPrints().contains("Field " + fieldName + " successfully changed to " + newValue + "!"));
    }

    @Then("system will respond with {string}")
    public void systemWillRespondWith(String expectedOutput) {
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();
        
        assertTrue(io.getPrints().contains(expectedOutput));
    }

    @Then("app lists a recommendation with author {string}, title {string}, description {string}, isbn {string}, and page count {string}")
    public void listingAddedBookRecommendation(String author, String title, String description, String isbn, String pageCount) {
        io = new StubIO(inputLines);

        dao = new InMemoryRecommendationDao();

        ui = new UserInterface(io, dao);
        
        ui.run();

        String addDate = java.time.LocalDate.now().toString();

        assertTrue(io.getPrints().contains(System.lineSeparator() + "Book 1" + System.lineSeparator()
            + "Author: " + author + System.lineSeparator()
            + "Title: " + title + System.lineSeparator()
            + "Description: " + description + System.lineSeparator()
            + "ISBN: " + isbn + System.lineSeparator()
            + "Page count: " + pageCount + System.lineSeparator()
            + "Added: " + addDate));
    }

    @Then("app lists a recommendation with title {string}, description {string} and url {string}")
    public void listingAddedVideoRecommendation(String title, String description, String url) {
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        String addDate = java.time.LocalDate.now().toString();

        assertTrue(io.getPrints().contains(System.lineSeparator() + "Video 1" + System.lineSeparator()
            + "Title: " + title + System.lineSeparator()
            + "URL: " + url + System.lineSeparator()
            + "Timestamps: " + System.lineSeparator()
            + "[]" + System.lineSeparator()
            + "Description: " + description + System.lineSeparator()
            + "Added: " + addDate));
    }

    @Then("app lists a recommendation with title {string}, timestamp {string} and comment {string}")
    public void listingAddedVideoRecommendationWithTimestamp(String title, String timestamp, String comment) {
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        String addDate = java.time.LocalDate.now().toString();

        assertTrue(io.getPrints().contains(System.lineSeparator() + "Video 1" + System.lineSeparator()
            + "Title: " + title + System.lineSeparator()
            + "URL: default url" + System.lineSeparator()
            + "Timestamps: " + System.lineSeparator()
            + "[Time: " + timestamp + ", Comment: " + comment + "]" + System.lineSeparator()
            + "Description: default description" + System.lineSeparator()
            + "Added: " + addDate));
    }

    @Then("app lists a recommendation with title {string}, author {string}, description {string} and url {string}")
    public void listingAddedBlogRecommendation(String title, String author, String description, String url) {
        System.err.println(String.join("\n", inputLines));

        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        String addDate = java.time.LocalDate.now().toString();

        assertTrue(io.getPrints().contains(System.lineSeparator() + "Blog 1" + System.lineSeparator()
            + "Author: " + author + System.lineSeparator()
            + "Title: " + title + System.lineSeparator()
            + "Description: " + description + System.lineSeparator()
            + "URL: " + url + System.lineSeparator()
            + "Added: " + addDate));
    }

    @Then("app lists a podcast recommendation with title {string}, name {string}, author {string} and description {string}")
    public void listingAddedPodcastRecommendation(String title, String name, String author, String description) {

        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        String addDate = java.time.LocalDate.now().toString();

        assertTrue(io.getPrints().contains(System.lineSeparator() + "Podcast 1" + System.lineSeparator()
            + "Podcast name: " + name + System.lineSeparator()
            + "Author: " + author + System.lineSeparator()
            + "Title: " + title + System.lineSeparator()
            + "Description: " + description + System.lineSeparator()
            + "Added: " + addDate));
    }

    @Then("app deletes a recommendation with the title {string}")
    public void deletingBookWorks(String deletedTitle) {
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        assertTrue(io.getPrints().contains("Recommendation added"));
        assertTrue(io.getPrints().contains("Recommendation deleted!"));

        for (BookRecommendation b : dao.getAllBookRecommendations()) {
            assertFalse(b.getTitle().equals(deletedTitle));
        }

        assertTrue(dao.getAllBookRecommendations().isEmpty());
    }

    @Then("app doesn't delete a recommendation with the title {string}")
    public void failingToDeleteWorks(String title) {
        inputLines.add("5");
        io = new StubIO(inputLines);
        dao = new InMemoryRecommendationDao();
        ui = new UserInterface(io, dao);
        ui.run();

        assertTrue(io.getPrints().contains("Recommendation added"));
        assertTrue(io.getPrints().contains("Recommendation with the given title doesn't exist! Try again: "));
    }
}
