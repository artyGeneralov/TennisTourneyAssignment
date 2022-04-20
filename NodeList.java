public class NodeList<T> {

	private Node<T> firstNode;

	// Constructor:
	public NodeList(T value) {
		firstNode = null;
	}

	// List Operations:
	public void insertAtFront(T value) {
		Node<T> newNode = new Node<T>(value);
		if (firstNode == null) {
			firstNode = newNode;
			return;
		}
		newNode.setNext(firstNode);
		this.firstNode = newNode;
	}

	public T removeFromBack() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException("Cannot remove from empty list.");

		T removedNodeValue;
		if (firstNode.getNext() == null) {
			removedNodeValue = firstNode.getValue();
			firstNode = null;
			return removedNodeValue;
		}
		
		Node<T> temp = firstNode.getNext();
		Node<T> current = firstNode;
		while (temp.getNext() != null) {
			temp = temp.getNext();
			current = current.getNext();
		}
		removedNodeValue = temp.getValue();
		current.setNext(null);
		return removedNodeValue;
	}

	// Other methods:
	public boolean isEmpty() {
		return this.firstNode == null;
	}

	public void print() {
		Node<T> temp = firstNode;
		int counter = 1;
		while (temp != null) {
			System.out.printf("%d.) %s\n", counter, temp);
			temp = temp.getNext();
			counter++;
		}
	}
}
