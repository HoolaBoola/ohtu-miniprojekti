/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendation_library.dao;

import recommendation_library.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anadis
 */
public class DatabaseRecommendationDao implements RecommendationDao {

    private String fileName;

    public DatabaseRecommendationDao(String filename) {
        this.fileName = filename;
        connect();
        createBookTable();
        createVideoTable();
        createTimeStampTable();
        createBlogTable();
        createPodcastTable();
        createTagTable();
        createConnectionTables();
    }

    private Connection connect() {
        // SQLite connection string  
        String url = "jdbc:sqlite:" + fileName;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());  
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private void createBookTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
            + " id integer PRIMARY KEY,\n"
            + " author TEXT NOT NULL,\n"
            + " title TEXT NOT NULL UNIQUE,\n"
            + " description TEXT,\n"
            + " isbn TEXT,\n"
            + " pageCount integer,\n"
            + " created TEXT"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createVideoTable() {
        String sql = "CREATE TABLE IF NOT EXISTS videos (\n"
            + " id integer PRIMARY KEY,\n"
            + " url TEXT NOT NULL,\n"
            + " title TEXT NOT NULL UNIQUE,\n"
            + " description TEXT,\n"
            + " created TEXT"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTimeStampTable() {
        String sql = "CREATE TABLE IF NOT EXISTS timestamps (\n"
            + " id integer PRIMARY KEY,\n"
            + " timestamp TEXT NOT NULL,\n"
            + " comment TEXT,\n"
            + " video_id INTEGER REFERENCES videos"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
//            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createPodcastTable() {
        String sql = "CREATE TABLE IF NOT EXISTS podcasts (\n"
            + " id integer PRIMARY KEY,\n"
            + " author TEXT NOT NULL,\n"
            + " title TEXT NOT NULL UNIQUE,\n"
            + " description TEXT,\n"
            + " name TEXT,\n"
            + " created TEXT"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createBlogTable() {
        String sql = "CREATE TABLE IF NOT EXISTS blogs (\n"
            + " id integer PRIMARY KEY,\n"
            + " url TEXT NOT NULL,\n"
            + " author TEXT NOT NULL,\n"
            + " title TEXT NOT NULL UNIQUE,\n"
            + " description TEXT,\n"
            + " created TEXT"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTagTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tags (\n"
            + " id integer PRIMARY KEY,\n"
            + " tagText TEXT NOT NULL"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createConnectionTables() {
        createBookConnectionTable();
        createVideoConnectionTable();
        createBlogConnectionTable();
        createPodcastConnectionTable();
    }

    private void createBookConnectionTable() {
        String booksTags = "CREATE TABLE IF NOT EXISTS booksTags (\n"
            + " books_id INTEGER REFERENCES books,\n"
            + " tags_id INTEGER REFERENCES tags"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(booksTags);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createVideoConnectionTable() {
        String videosTags = "CREATE TABLE IF NOT EXISTS videosTags (\n"
            + " videos_id INTEGER REFERENCES videos,\n"
            + " tags_id INTEGER REFERENCES tags"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(videosTags);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createBlogConnectionTable() {
        String blogsTags = "CREATE TABLE IF NOT EXISTS blogsTags (\n"
            + " blogs_id INTEGER REFERENCES blogs,\n"
            + " tags_id INTEGER REFERENCES tags"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(blogsTags);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createPodcastConnectionTable() {
        String podcastsTags = "CREATE TABLE IF NOT EXISTS podcastsTags (\n"
            + " podcasts_id INTEGER REFERENCES podcasts,\n"
            + " tags_id INTEGER REFERENCES tags"
            + ");";
        try {
            Connection connection = connect();
            Statement stmt = connection.createStatement();
            stmt.execute(podcastsTags);
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Insert a new recommendation into the database
     *
     * @param author
     * @param title
     * @param description
     * @param isbn
     */
    @Override
    public void createBookRecommendation(String author, String title, String description, String isbn, int pageCount) {
        String sql = "INSERT INTO books (author, title, description, isbn, pageCount, created) "
            + "VALUES (?,?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, author);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, isbn);
            statement.setInt(5, pageCount);
            statement.setString(6, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new recommendation into the database
     *
     * @param url
     * @param title
     * @param description
     */
    @Override
    public void createVideoRecommendation(String url, String title, String description) {
        String sql = "INSERT INTO videos (url, title, description, created) "
            + "VALUES (?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, url);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Add a new timestamp into the database
     *
     * @param videoId
     * @param timestamp
     * @param comment
     */
    @Override
    public void addTimeStampToVideo(int videoId, String timestamp, String comment) {
        String sql = "INSERT INTO timestamps (timestamp, comment, video_id) "
            + "VALUES (?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, timestamp);
            statement.setString(2, comment);
            statement.setInt(3, videoId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Insert a new recommendation into the database
     *
     * @param url
     * @param title
     * @param author
     * @param description
     */
    @Override
    public void createBlogRecommendation(String url, String title, String author, String description) {
        String sql = "INSERT INTO blogs (url, title, author, description, created) "
            + "VALUES (?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, url);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, description);
            statement.setString(5, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new recommendation into the database
     *
     * @param author
     * @param title
     * @param description
     * @param name
     */
    @Override
    public void createPodcastRecommendation(String author, String title, String description, String name) {
        String sql = "INSERT INTO podcasts (author, title, description, name, created) "
            + "VALUES (?,?,?,?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, author);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, name);
            statement.setString(5, java.time.LocalDate.now().toString());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fetch every book recommendation from attached database
     *
     * @return List of recommendations
     */
    @Override
    public List<BookRecommendation> getAllBookRecommendations() {
        ArrayList<BookRecommendation> books = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM books");
            while (result.next()) {
                books.add(new BookRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("title"), result.getString("description"),
                    result.getString("isbn"), result.getInt("pageCount"), result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public List<VideoRecommendation> getAllVideoRecommendations() {
        ArrayList<VideoRecommendation> videos = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM videos");
            while (result.next()) {
                videos.add(new VideoRecommendation(result.getInt("id"), result.getString("url"),
                    result.getString("title"), result.getString("description"),
                    result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }

    @Override
    public List<BlogRecommendation> getAllBlogRecommendations() {
        ArrayList<BlogRecommendation> blogs = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM blogs");
            while (result.next()) {
                blogs.add(new BlogRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("url"), result.getString("title"),
                    result.getString("description"), result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return blogs;
    }

    @Override
    public List<PodcastRecommendation> getAllPodcastRecommendations() {
        ArrayList<PodcastRecommendation> podcasts = new ArrayList<>();
        try {
            Connection connection = this.connect();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM podcasts");
            while (result.next()) {
                podcasts.add(new PodcastRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("title"), result.getString("description"),
                    result.getString("name"), result.getString("created")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return podcasts;
    }

    @Override
    public List<TimeMemory> getAllTimestampsForVideo(int videId) {
        ArrayList<TimeMemory> timestamps = new ArrayList<>();
        String sql = "SELECT * FROM timestamps WHERE video_id = ?";
        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, videId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                timestamps.add(new TimeMemory(result.getInt("id"), result.getString("timestamp"),
                    result.getString("comment"), result.getInt("video_id")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return timestamps;
    }

    @Override
    public void editBookRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE books SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editVideoRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE videos SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement preparedstmt = conn.prepareStatement(sql);
            preparedstmt.setString(1, newValue);
            preparedstmt.setString(2, title);
            preparedstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteBookByTitle(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteVideoByTitle(String title) {
        int videoId = getVideoIdByTitle(title);

        String deleteVideo = "DELETE FROM videos WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteVideo);
            stmt.setString(1, title);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        deleteVideoTimeStamps(videoId);
    }

    private void deleteVideoTimeStamps(int videoId) {
        String deleteTimeStamps = "DELETE FROM timestamps WHERE video_id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteTimeStamps);
            stmt.setInt(1, videoId);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editTimestampForVideo(int videoId, int timeStampId, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE timestamps SET " + fieldToBeEdited + " = ? WHERE video_id = ? AND id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement preparedstmt = conn.prepareStatement(sql);
            preparedstmt.setString(1, newValue);
            preparedstmt.setInt(2, videoId);
            preparedstmt.setInt(3, timeStampId);
            preparedstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteTimestamp(int videoId, int timeStampId) {
        String deleteTimeStamps = "DELETE FROM timestamps WHERE video_id = ? AND id = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteTimeStamps);
            stmt.setInt(1, videoId);
            stmt.setInt(2, timeStampId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getVideoIdByTitle(String title) {
        String sql = "SELECT id FROM videos WHERE title = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public int getBookIdByTitle(String title) {
        String sql = "SELECT id FROM books WHERE title = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public int getBlogIdByTitle(String title) {
        String sql = "SELECT id FROM blogs WHERE title = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public int getPodcastIdByTitle(String title) {
        String sql = "SELECT id FROM podcasts WHERE title = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public int getTimestampIdByTitle(int videoId, String timestamp) {
        String sql = "SELECT id FROM timestamps WHERE video_id = ? AND timestamp = ?";
        int id = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, videoId);
            pstmt.setString(2, timestamp);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                id = result.getInt("id");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    @Override
    public void editBlogRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE blogs SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editPodcastRecommendation(String title, String fieldToBeEdited, String newValue) {
        String sql = "UPDATE podcasts SET " + fieldToBeEdited + " = ? WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newValue);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteBlogByTitle(String title) {
        String deleteBlog = "DELETE FROM blogs WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deleteBlog);
            stmt.setString(1, title);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deletePodcastByTitle(String title) {
        String deletePodcast = "DELETE FROM podcasts WHERE title = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(deletePodcast);
            stmt.setString(1, title);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tag> getAllTagsForBook(int bookId) {
        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT id, tagText FROM tags INNER JOIN booksTags ON tags.id = booksTags.tags_id WHERE booksTags.books_id = ?";
        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, bookId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                tags.add(new Tag(result.getInt("id"), result.getString("tagText")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTagsForVideo(int videoId) {
        ArrayList<Tag> tags = new ArrayList<>();
        
        String sql = "SELECT * FROM tags INNER JOIN videosTags ON tags.id = videosTags.tags_id WHERE videosTags.books_id = ?";

        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, videoId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                tags.add(new Tag(result.getInt("id"), result.getString("tagText")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTagsForBlog(int blogId) {
        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tags INNER JOIN blogsTags ON tags.id = blogsTags.tags_id WHERE blogsTags.books_id = ?";

        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, blogId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                tags.add(new Tag(result.getInt("id"), result.getString("tagText")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> getAllTagsForPodcast(int podcastId) {
        ArrayList<Tag> tags = new ArrayList<>();

        String sql = "SELECT * FROM tags INNER JOIN podcastsTags ON tags.id = podcastsTags.tags_id WHERE podcastsTags.books_id = ?";

        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            pstatement.setInt(1, podcastId);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                tags.add(new Tag(result.getInt("id"), result.getString("tagText")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public void addTagToBook(int bookId, String tagText) {
        int tagId = getTagId(tagText);
        if (tagId == 0) {
            createTag(tagText);
            tagId = getTagId(tagText);
        }
        String booksTags = "INSERT INTO booksTags(books_id, tags_id) VALUES(?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(booksTags);
            statement.setInt(1, bookId);
            statement.setInt(2, tagId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addTagToVideo(int videoId, String tagText) {
        int tagId = getTagId(tagText);
        if (tagId == 0) {
            createTag(tagText);
            tagId = getTagId(tagText);
        }
        String booksTags = "INSERT INTO videosTags(videos_id, tags_id) VALUES(?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(booksTags);
            statement.setInt(1, videoId);
            statement.setInt(2, tagId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public void addTagToBlog(int blogId, String tagText) {
        int tagId = getTagId(tagText);
        if (tagId == 0) {
            createTag(tagText);
            tagId = getTagId(tagText);
        }
        String booksTags = "INSERT INTO blogsTags(blogs_id, tags_id) VALUES(?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(booksTags);
            statement.setInt(1, blogId);
            statement.setInt(2, tagId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addTagToPodcast(int podcastId, String tagText) {
        int tagId = getTagId(tagText);
        if (tagId == 0) {
            createTag(tagText);
            tagId = getTagId(tagText);
        }
        String booksTags = "INSERT INTO podcastsTags(podcasts_id, tags_id) VALUES(?,?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(booksTags);
            statement.setInt(1, podcastId);
            statement.setInt(2, tagId);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void createTag(String tagText) {
        String tag = "INSERT INTO tags(tagText) "
            + "VALUES(?)";
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(tag);
            statement.setString(1, tagText);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getTagId(String tagText) {
        String getTagId = "SELECT id FROM tags WHERE tagText = ?";
        int tagId = 0;
        try {
            Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(getTagId);
            statement.setString(1, tagText);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                tagId = result.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tagId;
    }

    @Override
    public List<Tag> getAllTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT id, tagText FROM tags";
        try {
            Connection connection = this.connect();
            PreparedStatement pstatement = connection.prepareStatement(sql);
            ResultSet result = pstatement.executeQuery();
            while (result.next()) {
                tags.add(new Tag(result.getInt("id"), result.getString("tagText")));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Recommendation> getRecommendationsWithTag(String tag) {
        ArrayList<Recommendation> list = new ArrayList<>();

        list.addAll(getBooksWithTag(tag));
        list.addAll(getVideosWithTag(tag));
        list.addAll(getPodcastsWithTag(tag));
        list.addAll(getBlogsWithTag(tag));
        return list;
    }

    public List<BookRecommendation> getBooksWithTag(String tag) {
        String sql = "SELECT Books.id, author, title, description, isbn, pagecount, created" +
            " from Books" +
            " join BooksTags on Books.id = BooksTags.books_id" +
            " join Tags on tags_id = Tags.id " +
            " where tagText = ?";

        ArrayList<BookRecommendation> books = new ArrayList<>();
        Connection connection = this.connect();
        ResultSet result = getResultsForTagSearch(tag, sql, connection);
        try {
            while (result.next()) {
                books.add(new BookRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("title"), result.getString("description"),
                    result.getString("isbn"), result.getInt("pageCount"), result.getString("created")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<VideoRecommendation> getVideosWithTag(String tag) {
        String sql = "SELECT Videos.id, url, title, description, created" +
            " from Videos" +
            " join VideosTags on Videos.id = VideosTags.videos_id" +
            " join tags on tags_id = tags.id " +
            " where tags.tagText = ?";

        ArrayList<VideoRecommendation> videos = new ArrayList<>();
        Connection connection = this.connect();
        ResultSet result = getResultsForTagSearch(tag, sql, connection);
        try {
            while (result.next()) {
                videos.add(new VideoRecommendation(result.getInt("id"), result.getString("url"),
                    result.getString("title"), result.getString("description"),
                    result.getString("created")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public List<PodcastRecommendation> getPodcastsWithTag(String tag) {
        String sql = "SELECT Podcasts.id, author, title, description, name, created" +
            " from Podcasts" +
            " join PodcastsTags on Podcasts.id = PodcastsTags.podcasts_id" +
            " join tags on tags_id = tags.id " +
            " where tags.tagText = ?";
        ArrayList<PodcastRecommendation> podcasts = new ArrayList<>();
        Connection connection = this.connect();
        ResultSet result = getResultsForTagSearch(tag, sql, connection);
        try {
            while (result.next()) {
                podcasts.add(new PodcastRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("title"), result.getString("description"),
                    result.getString("name"), result.getString("created")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return podcasts;
    }


    public List<BlogRecommendation> getBlogsWithTag(String tag) {
        String sql = "SELECT Blogs.id, url, author, title, description, created" +
            " from Blogs" +
            " join BlogsTags on Blogs.id = BlogsTags.blogs_id" +
            " join tags on tags_id = tags.id " +
            " where tags.tagText = ?";
        ArrayList<BlogRecommendation> blogs = new ArrayList<>();
        Connection connection = this.connect();
        ResultSet result = getResultsForTagSearch(tag, sql, connection);
        try {
            while (result.next()) {
                blogs.add(new BlogRecommendation(result.getInt("id"), result.getString("author"),
                    result.getString("url"), result.getString("title"),
                    result.getString("description"), result.getString("created")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    private ResultSet getResultsForTagSearch(String tag, String sql, Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, tag);

            ResultSet result = statement.executeQuery();
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
