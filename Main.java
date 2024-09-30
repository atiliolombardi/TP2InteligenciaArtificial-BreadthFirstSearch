import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

class TreeNode {
    //Clase nodo
    String value;
    List<TreeNode> children;

    public TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}

class Tree {
    TreeNode root;

    public Tree(String rootValue, int depth) {
        this.root = new TreeNode(rootValue); //Creamos el nodo raiz
        buildTree(root, depth, 1); //Generamos el resto del arbol recursivamente
    }

    // Construir el árbol de forma recursiva
    private void buildTree(TreeNode node, int maxDepth, int currentDepth) {
        //Checkeamos si estamos en el nodo raiz
        if (currentDepth == 1) {
            // Nodo raiz:
            // Creamos dos nodos hijos
            TreeNode leftChild = new TreeNode("L" + currentDepth);
            TreeNode rightChild = new TreeNode("R" + currentDepth);

            node.addChild(leftChild);
            node.addChild(rightChild);

            // Construimos recursivamente las ramas
            buildTree(leftChild, maxDepth, currentDepth + 1);
            buildTree(rightChild, maxDepth, currentDepth + 1);
            return;
        }
        //Checkeamos si alcanzamos la profundidad maxima
        if (currentDepth == maxDepth) {
            // Alcanzamos la profundidad maxima, asi que hemos finalizado
            return;
        }

        // Creamos un nodo hijo
        TreeNode child = new TreeNode(node.value.substring(0, 1) + (Integer.parseInt(node.value.substring(1, node.value.length())) + 1));
        node.addChild(child);

        // Construimos recursivamente la rama
        buildTree(child, maxDepth, currentDepth + 1);
    }

    // Método para mostrar el árbol por consola
    public void printTree() {
        printNode(root, 0);
    }

    // Función recursiva para mostrar el árbol
    private void printNode(TreeNode node, int level) {
        if (node == null) {
            return;
        }

        // Imprimimos el nodo con indentación según el nivel
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
        System.out.println(node.value);

        // Mostramos los hijos recursivamente
        for (TreeNode child : node.children) {
            printNode(child, level + 1);
        }
    }

    // Método para generar un "A" aleatorio
    public void assignRandomNode(String assignedValue) {
        List<TreeNode> nodes = new ArrayList<>();
        collectNodes(root, nodes); // Recolectar nodos
        if (nodes.size() > 1) { // Asegurarse de que haya al menos un nodo para renombrar
            Random rand = new Random();
            int randomIndex = rand.nextInt(nodes.size() - 1) + 1; // Elegir un índice aleatorio que no sea 0 (raíz)
            nodes.get(randomIndex).value = assignedValue; // Cambiar el valor del nodo
        }
    }

    // Método auxiliar para recolectar nodos en una lista
    private void collectNodes(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(node); // Añadir el nodo actual a la lista
        for (TreeNode child : node.children) {
            collectNodes(child, nodes); // Recolectar nodos de los hijos
        }
    }

    // Método de búsqueda por anchura
    public void breadthFirstSearch(String target) {
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<List<String>> paths = new LinkedList<>(); // Cola para almacenar los caminos
        int order = 1; // Contador para el orden de exploración

        queue.add(root); //Agrega el nodo raíz a la cola de nodos para comenzar la exploración
        paths.add(new ArrayList<>()); // Agregar camino inicial

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll(); // Extrae el primer nodo de la cola para su exploración
            List<String> currentPath = paths.poll(); // Obtener el camino actual
            currentPath.add(currentNode.value); // Agregar el nodo actual al camino

            // Imprimir el orden en que se explora el nodo
            System.out.println("Explorando nodo " + order + ": " + currentNode.value);
            order++;

            //Revisamos si el nodo explorado es el nodo buscado
            if (currentNode.value.equals(target)) {
                System.out.println("Encontrado el nodo: " + target);
                System.out.println("Camino: " + currentPath);
                return; // Salir al encontrar el nodo
            }

            // Agregar hijos a la cola
            for (TreeNode child : currentNode.children) {
                queue.add(child);
                paths.add(new ArrayList<>(currentPath)); // Copiar el camino actual para el hijo
            }
        }

        System.out.println("Nodo no encontrado: " + target);
    }
}

public class Main {
    public static void main(String[] args) {
        int depth = 4;  // Número configurable de profundidad del árbol
        Tree tree = new Tree("B", depth); //Crear árbol con raiz "B"

        // Cambiar el valor de un nodo aleatorio a "A"
        tree.assignRandomNode("A");

        // Mostrar el árbol por consola
        tree.printTree();

        // Búsqueda por anchura para encontrar el nodo "A"
        tree.breadthFirstSearch("A");
    }
}
