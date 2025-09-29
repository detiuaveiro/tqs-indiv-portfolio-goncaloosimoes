import org.example.TqsStack;
import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TqsStackTest {

    private TqsStack<String> stack;

    @BeforeEach
    void setUp() {
        stack = new TqsStack<>();
    }

    @Test
    @DisplayName("Stack is empty on construction")
    void emptyOnConstruction() {
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Stack has size 0 on construction")
    void sizeZeroOnConstruction() {
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("After n pushes to an empty stack, size is n and stack is not empty")
    void afterNPushes() {
        stack.push("first");
        stack.push("second");
        stack.push("third");

        assertEquals(3, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("If one pushes x then pops, the value popped is x")
    void pushThenPop() {
        String element = "test";
        stack.push(element);
        assertEquals(element, stack.pop());
    }

    @Test
    @DisplayName("If one pushes x then peeks, the value returned is x and size remains the same")
    void pushThenPeek() {
        String element = "test";
        stack.push(element);
        int sizeBefore = stack.size();

        assertEquals(element, stack.peek());
        assertEquals(sizeBefore, stack.size());
    }

    @Test
    @DisplayName("If the size is n, then after n pops, the stack is empty and has size 0")
    void popToEmpty() {
        // Push some elements
        stack.push("first");
        stack.push("second");
        stack.push("third");

        // Pop all elements
        stack.pop();
        stack.pop();
        stack.pop();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Popping from an empty stack throws NoSuchElementException")
    void popEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    @DisplayName("Peeking into an empty stack throws NoSuchElementException")
    void peekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @Test
    @DisplayName("PopTopN returns nth element and removes n elements from stack")
    void popTopNTest() {
        stack.push("first");
        stack.push("second");
        stack.push("third");
        stack.push("fourth");

        String thirdElement = stack.popTopN(2); // pops first and second, returns third

        assertEquals("third", thirdElement);
        assertEquals(2, stack.size()); // size = 2 (third and fourth remain)
        
        // Test exception cases
        assertThrows(IllegalArgumentException.class, () -> stack.popTopN(-2));
        assertThrows(IllegalArgumentException.class, () -> stack.popTopN(0));
        assertThrows(NoSuchElementException.class, () -> stack.popTopN(5));
    }
}
