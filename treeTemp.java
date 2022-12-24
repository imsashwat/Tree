package Tree;

import java.util.*;

import org.w3c.dom.NodeList;

import LinkedList.stacksll.stack;

public class treeTemp {
    public static class node {
        int data;
        ArrayList<node> children = new ArrayList<>();
    }

    public static void display(node node) {
        String str = node.data + "->";

        for (node child : node.children) {
            str += child.data + " ";
        }
        str += ".";
        System.out.println(str);

        for (node child : node.children) {
            display(child);
        }
    }

    public static int maxNode(node node) {
        int maxBefore = 0;

        for (node child : node.children) {
            // int temp= child.data;
            System.out.println("edge pre maxbefore" + maxBefore);
            int x = maxNode(child);
            System.out.println("edge post maxbefore" + maxBefore);

            maxBefore = Math.max(x, maxBefore);
            System.out.println("edge last maxbefore" + maxBefore);
            // if(maxBefore>x) {
            // maxBefore = x;
            // }
        }

        maxBefore = Math.max(node.data, maxBefore);

        // if(maxBefore<node.data ) {
        // maxBefore = node.data;
        // }
        return maxBefore;
    }

    public static void linearize(node node) {
        for(node child : node.children) {
            linearize(child);
        }
        System.out.println("node is " + node.data);
        while(node.children.size() > 1) {
            
            node lc = node.children.remove(node.children.size()-1); //last child
            System.out.println("lc is " + lc.data);
            node sl = node.children.get(node.children.size()-1); //secondlast child
            node slt = getTail(sl); //secondlasttail
            slt.children.add(lc);
        }
    }



    public static node getTail(node node) {
        while(node.children.size() == 1) {
            node = node.children.get(0);
        }
        return node;
    }

    public static boolean findElement(node node, int data) {
        if(node.data == data) {
            return true;
        }

        for(node child: node.children) {
            boolean fic = findElement(child, data);

            if(fic) {
                return true;
            }
        }
        return false;
    }

    public static void levelOrder(node node) {
        Stack<node> st2 = new Stack<>();

        for (node child : node.children) {
            // System.out.println("edge pre maxbefore" + maxBefore);
            st2.push(child);
        }
        for (node child : node.children) {
            // System.out.println("edge pre maxbefore" + maxBefore);
            levelOrder(child);
        }

    }

    public static boolean areMirror(node n1, node n2) {
        if(n1.children.size() != n2.children.size()) {
            return false;
        }

        for(int i =0; i<n1.children.size(); i++) {
            int j = n1.children.size()-1-i;
            node c1 = n1.children.get(i);
            node c2 = n2.children.get(j);
            if(areMirror(c1, c2) == false) {
                return false; //once false aa gya to true(line117) pe kbhi nhi phochega control
            }
        }
        return true; 
    }

    public static ArrayList<Integer> nodetoRoot(node node,int data) {
        if(node.data == data) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(node.data);
            return list;
        }

        for(node child: node.children) {
            ArrayList<Integer> ptc = nodetoRoot(child, data);
            if(ptc.size()>0) {
                ptc.add(node.data);
                return ptc;
            }
        }

        return new ArrayList<>();
    }

    static int msn =0;
    static int ms = Integer.MIN_VALUE;

    static int retSumandCalculateMSST(node node) {
        int sum =0;

        for(node child:node.children) {
            System.out.println("child"+child.data);
            int csum = retSumandCalculateMSST(child);
            System.out.println("sum before & child konsa"+sum + child.data);
            sum+=csum;
            System.out.println("sum after & child konsa"+sum + child.data);
        }

        //System.out.println("child may be 30"+sum);
        sum+=node.data;
        System.out.println("sum"+sum);
        if(sum>ms) {
            msn = node.data;
            ms = sum;
        }

        return sum;
        //return sum;
    }

    static class Pair {
        node node;
        int state;

        Pair(node node, int state) {
            this.node = node;
            this.state = state;
        }
    }

    public static void iterativePreAndPost(node node) {
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(node, -1));
        

        String pre = " ";
        String post = " ";

        while(st.size()>0) {
            Pair top = st.peek();
            System.out.println(" peek dekhlo" + top.node.data + top.state );
            if(top.state == -1) {
                pre += top.node.data;
                top.state++;
            }
            else if(top.state == top.node.children.size()) { //50 ka 0 children.size k equal h -postorder
                post +=top.node.data + " ";
                st.pop(); //postorder se print krke stack se remove kr diya
            }
            else {
                Pair cp = new Pair(top.node.children.get(top.state), -1); //yaha se child stack me daalenge
                st.push(cp);

                top.state++; //is instance me top 30 h at this moment to uska ++ krke 2 hua state
            }
        }
        //System.out.println(pre);
        //System.out.println(post);
    }

    public static void main(String[] args) {
        int[] arr = { 10, 20, -1, 30, 50, -1, 60, -1, -1, 40, -1 };
        node root = null;
        Stack<node> st = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == -1) {
                st.pop();
            }

            else {
                node t = new node();
                t.data = arr[i];

                if (st.size() > 0) {
                    st.peek().children.add(t);
                    st.push(t);

                } else {
                    root = t;
                    st.push(t);
                }
            }
        }
        
       // display(root);
        //System.out.println("maximum is " + maxNode(root));
        //levelOrder(root);
        //linearize(root);
       // display(root);
        
       // System.out.println(nodetoRoot(root, 40));
        //retSumandCalculateMSST(root);
       // System.out.println(msn + "@" + ms);
        iterativePreAndPost(root);
        ;
    }
}
