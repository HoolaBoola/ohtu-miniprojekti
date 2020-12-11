/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendation_library.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import recommendation_library.dao.RecommendationDao;

/**
 *
 * @author anadis
 */
public class DatabaseService {

    private RecommendationDao dao;

    public DatabaseService(RecommendationDao dao) {
        this.dao = dao;
    }

    public boolean bookTitleAlreadyExists(String title) {
        List<BookRecommendation> books = dao.getAllBookRecommendations();
        for (BookRecommendation book : books) {
            if (book.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean videoTitleAlreadyExists(String title) {
        List<VideoRecommendation> videos = dao.getAllVideoRecommendations();
        for (VideoRecommendation video : videos) {
            if (video.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean blogTitleAlreadyExists(String title) {
        List<BlogRecommendation> blogs = dao.getAllBlogRecommendations();
        for (BlogRecommendation blog : blogs) {
            if (blog.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean podcastTitleAlreadyExists(String title) {
        List<PodcastRecommendation> podcasts = dao.getAllPodcastRecommendations();
        for (PodcastRecommendation podcast : podcasts) {
            if (podcast.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean timeStampAlreadyExists(String videoTitle, String time) {
        int videoID = dao.getVideoIdByTitle(videoTitle);
        List<TimeMemory> timestampsOfVideo = dao.getAllTimestampsForVideo(videoID);
        for (TimeMemory t : timestampsOfVideo) {
            if (t.getTimeStamp().equals(time)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean tagAlreadyExists(String tagText) {
        return dao.getTagId(tagText) == 0;
    }

    /**
     * Create book recommendation
     *
     * @param author
     * @param title
     * @param isbn
     * @param pageCount
     * @param description
     * @param tagTexts
     * 
     * @return success/failure
     */
    public boolean addBook(String author, String title, String description, String isbn, int pageCount, List<String> tagTexts) {
        if (bookTitleAlreadyExists(title)) {
            return false;
        }
        dao.createBookRecommendation(author, title, description, isbn, pageCount);
        int bookId = dao.getBookIdByTitle(title);
        for (String tagText : tagTexts) {
            if (!tagAlreadyExists(tagText)) {
                dao.createTag(tagText);
            }
            dao.addTagToBook(bookId, tagText);
        }
        return true;
    }

    /**
     * Create video recommendation
     *
     * @param url
     * @param title
     * @param description
     * @param tagTexts
     * 
     * @return success/failure
     */
    public boolean addVideo(String url, String title, String description, List<String> tagTexts) {
        if (videoTitleAlreadyExists(title)) {
            return false;
        }
        dao.createVideoRecommendation(url, title, description);
        int videoId = dao.getVideoIdByTitle(title);
        for (String tagText : tagTexts) {
            if (!tagAlreadyExists(tagText)) {
                dao.createTag(tagText);
            }
            dao.addTagToVideo(videoId, tagText);
        }
        return true;
    }
    
    /**
     * Create blog recommendation
     *
     * @param url
     * @param author
     * @param title
     * @param description
     * @param tagTexts
     * 
     * @return success/failure
     */
    public boolean addBlog(String url, String title, String author, String description, List<String> tagTexts) {
        if (this.blogTitleAlreadyExists(title)) {
            return false;
        }
        dao.createBlogRecommendation(url, title, author, description);
        int blogId = dao.getBlogIdByTitle(title);
        for (String tagText : tagTexts) {
            if (!tagAlreadyExists(tagText)) {
                dao.createTag(tagText);
            }
            dao.addTagToBlog(blogId, tagText);
        }
        return true;
    }
    
    /**
     * Create podcast recommendation
     *
     * @param author
     * @param title
     * @param description
     * @param name
     * @param tagTexts
     * 
     * @return success/failure
     */
    public boolean addPodcast(String author, String title, String description, String name, List<String> tagTexts) {
        if (this.podcastTitleAlreadyExists(title)) {
            return false;
        }
        dao.createPodcastRecommendation(author, title, description, name);
        int podcastId = dao.getPodcastIdByTitle(title);
        for (String tagText : tagTexts) {
            if (!tagAlreadyExists(tagText)) {
                dao.createTag(tagText);
            }
            dao.addTagToPodcast(podcastId, tagText);
        }
        return true;
    }

    public List<Recommendation> getAllRecommendationsSortedByCreatedDate() {
        List<Recommendation> recommendations = new ArrayList<>();
        for (Recommendation r : getAllBookRecommendations()) {
            recommendations.add(r);
        }
        for (Recommendation r : getAllVideoRecommendations()) {
            recommendations.add(r);
        }
        for (Recommendation r : getAllBlogRecommendations()) {
            recommendations.add(r);
        }
        for (Recommendation r : getAllPodcastRecommendations()) {
            recommendations.add(r);
        }
        Collections.sort(recommendations);
        return recommendations;
    }

    public List<BookRecommendation> getAllBookRecommendations() {
        List<BookRecommendation> books = dao.getAllBookRecommendations();
        return books;
    }

    public List<VideoRecommendation> getAllVideoRecommendations() {
        List<VideoRecommendation> videos = dao.getAllVideoRecommendations();
        return videos;
    }
    
    public List<BlogRecommendation> getAllBlogRecommendations() {
        List<BlogRecommendation> blogs = dao.getAllBlogRecommendations();
        return blogs;
    }
    
    public List<PodcastRecommendation> getAllPodcastRecommendations() {
        List<PodcastRecommendation> pods = dao.getAllPodcastRecommendations();
        return pods;
    }

    public void editBookRecommendation(String title, String fieldToChange, String newValue) {
        this.dao.editBookRecommendation(title, fieldToChange, newValue);
    }
    
    public void editVideoRecommendation(String title, String fieldToChange, String newValue) {
        this.dao.editVideoRecommendation(title, fieldToChange, newValue);
    }
    
    public void editBlogRecommendation(String title, String fieldToChange, String newValue) {
        this.dao.editBlogRecommendation(title, fieldToChange, newValue);
    }
    
    public void editPodcastRecommendation(String title, String fieldToChange, String newValue) {
        this.dao.editPodcastRecommendation(title, fieldToChange, newValue);
    }

    public boolean deleteBookRecommendation(String title) {
        if (bookTitleAlreadyExists(title)) {
            dao.deleteBookByTitle(title);
            return true;
        }
        return false;
    }
    
    public boolean deleteVideoRecommendation(String title) {
        if (videoTitleAlreadyExists(title)) {
            dao.deleteVideoByTitle(title);
            return true;
        }
        return false;
    }
    
    public boolean deleteBlogRecommendation(String title) {
        if (blogTitleAlreadyExists(title)) {
            dao.deleteBlogByTitle(title);
            return true;
        }
        return false;
    }
    
    public boolean deletePodcastRecommendation(String title) {
        if (podcastTitleAlreadyExists(title)) {
            dao.deletePodcastByTitle(title);
            return true;
        }
        return false;
    }
    
    public boolean addTimeStampToVideo(String timestamp, String comment, String videoTitle) {
        int id = dao.getVideoIdByTitle(videoTitle);
        if (id == 0) {
            return false;
        }
        dao.addTimeStampToVideo(id, timestamp, comment);
        return true;
    }
    
    public List<TimeMemory> getTimestampsForVideo(String videoTitle) {
        List<TimeMemory> timestamps = new ArrayList<>();
        int id = dao.getVideoIdByTitle(videoTitle);
        if (id == 0) {
            return timestamps;
        }
        for (TimeMemory timestamp : dao.getAllTimestampsForVideo(id)) {
            timestamps.add(timestamp);
        }
        return timestamps;
    }
    
    public boolean editTimeStamp(String videoTitle, String timeStamp, String fieldToChange, String newValue) {
        int videoId = dao.getVideoIdByTitle(videoTitle);
        if (videoId == 0) {
            return false;
        }
        int timestampId = dao.getTimestampIdByTitle(videoId, timeStamp);
        if (timestampId == 0) {
            return false;
        }
        dao.editTimestampForVideo(videoId, timestampId, fieldToChange, newValue);
        return true;
    }
    
    public boolean deleteTimeStamp(String videoTitle, String timeStamp) {
        int videoId = dao.getVideoIdByTitle(videoTitle);
        if (videoId == 0) {
            return false;
        }
        int timestampId = dao.getTimestampIdByTitle(videoId, timeStamp);
        if (timestampId == 0) {
            return false;
        }
        dao.deleteTimestamp(videoId, timestampId);
        return true;
    }
    
    public boolean checkIdForZero(int id) {
        return id == 0 ? true : false;
    }
    
    public boolean addTagToBook(String bookTitle, String tagText) {
        int bookId = dao.getBookIdByTitle(bookTitle);
        System.err.println(bookId);

        dao.addTagToBook(bookId, tagText);
        return !checkIdForZero(bookId);
    }
    
    public boolean addTagToVideo(String videoTitle, String tagText) {
        int videoId = dao.getVideoIdByTitle(videoTitle);
        dao.addTagToVideo(videoId, tagText);
        return !checkIdForZero(videoId);
    }
    
    public boolean addTagToBlog(String blogTitle, String tagText) {
        int blogId = dao.getBlogIdByTitle(blogTitle);
        dao.addTagToBlog(blogId, tagText);
        return !checkIdForZero(blogId);
    }
    
    public boolean addTagToPodcast(String podcastTitle, String tagText) {
        int podcastId = dao.getPodcastIdByTitle(podcastTitle);
        dao.addTagToPodcast(podcastId, tagText);
        return !checkIdForZero(podcastId);
    }
    
    public List<Tag> getAllTags() {
        return dao.getAllTags();
    }

    public List<Recommendation> getRecommendationsWithTag(String tag) {
        ArrayList<Recommendation> list = new ArrayList<>();

        list.addAll(getBooksWithTag(tag));
        list.addAll(getVideosWithTag(tag));
        list.addAll(getPodcastsWithTag(tag));
        list.addAll(getBlogsWithTag(tag));
        return list;
    }

    public List<BookRecommendation> getBooksWithTag(String tag) {
        return dao.getBooksWithTag(tag);
    }

    public List<VideoRecommendation> getVideosWithTag(String tag) {

        return dao.getVideosWithTag(tag);
    }

    public List<PodcastRecommendation> getPodcastsWithTag(String tag) {

        return dao.getPodcastsWithTag(tag);
    }

    public List<BlogRecommendation> getBlogsWithTag(String tag) {

        return dao.getBlogsWithTag(tag);
    }
    
}
