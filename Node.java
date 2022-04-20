
public class Node<T> {
	private T value;
	private Node<T> nextNode;

	// Constructors:
	public Node(T value) {
		this(value, null);
	}

	public Node(T value, Node<T> next) {
		setValue(value);
		this.nextNode = next;
	}

	
	// Getters & Setters:
	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getNext() {
		return this.nextNode;
	}
	
	public void setNext(Node<T> next) {
		this.nextNode = next;
	}
	
	public T getValue() {
		return this.value;
	}


	public String toString() {
		return new String(this.value.toString());
	}

	public boolean hasNext() {
		return this.nextNode != null;
	}

}
