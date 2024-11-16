package telran.forum.test;

import telran.forum.dao.Forum;
import telran.forum.dao.ForumImpl;
import telran.forum.model.Post;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ForumTest {
    private Forum forum;
    private Post[] posts;
    private final int capacity = 100;
    private final Comparator<Post> comparator = (p1,p2) -> {
        int res = Integer.compare(p1.getPostId(), p2.getPostId());
        return res;
    };

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        posts = new Post[6];
        posts[0] = new Post(1, "Title1", "Author1", "Content1");
        posts[1] = new Post(2, "Title2", "Author1", "Content2");
        posts[2] = new Post(3, "Title3", "Author2", "Content3");
        posts[3] = new Post(4, "Title4", "Author2", "Content4");
        posts[4] = new Post(5, "Title5", "Author3", "Content5");
        posts[5] = new Post(6, "Title6", "Author3", "Content6");
        forum = new ForumImpl(capacity);
        for (int i = 0; i < posts.length - 1; i++) {
            forum.addPost(posts[i]);
        }

    }

    @org.junit.jupiter.api.Test
    void testAddPost() {
        assertFalse(forum.addPost(null));
        assertFalse(forum.addPost(posts[2]));
        assertTrue(forum.addPost(posts[5]));
        assertEquals(6, forum.size());
        assertFalse(forum.addPost(new Post(5, "Title15", "Author1", "Content24")));
    }

    @org.junit.jupiter.api.Test
    void testRemovePost() {
        assertFalse(forum.removePost(6));
        assertTrue(forum.removePost(3));
        assertEquals(4, forum.size());
    }

    @org.junit.jupiter.api.Test
    void testUpdatePost() {
        assertTrue(forum.updatePost(1, "newContent"));
        assertEquals("newContent", forum.getPostById(1).getContent());
    }

    @org.junit.jupiter.api.Test
    void testGetPostById() {
        assertEquals(posts[2], forum.getPostById(3));
        assertNull(forum.getPostById(6));
    }

    @org.junit.jupiter.api.Test
    void testGetPostsByAuthor() {
        Post[] actual = forum.getPostsByAuthor("Author1");
        Arrays.sort(actual, comparator);
        Post[] expected = {posts[0], posts[1]};
        assertArrayEquals(expected, actual);

    }

    @org.junit.jupiter.api.Test
    void testGetPostsByAuthorAndDate() {

    }

    @org.junit.jupiter.api.Test
    void testSize() {
        assertEquals( 5, forum.size());
    }
}