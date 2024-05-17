public class intList {
    class intNode {
        private int num;
        private intNode next;

        public intNode(intList this$0, int num, intNode next) {
            this.num = num;
            this.next = next;
        }

        public int getNum() {
            return this.num;
        }
    }

    private intNode head = null;

    public Boolean isEmpty() {
        if (this.head == null)
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public int getSize() {
        if (isEmpty().booleanValue())
            return 0;
        intNode runner = this.head;
        int sizeNum = 0;
        while (runner != null) {
            runner = runner.next;
            sizeNum++;
        }
        return sizeNum;
    }

    public intNode getNode(int pos) {
        if (getSize() > pos) {
            intNode runner = this.head;
            while (pos > 0) {
                pos--;
                runner = runner.next;
            }
            return runner;
        }
        return null;
    }

    public void addNode(int num) {
        intNode newNode = new intNode(this, num, null);
        if (this.head == null) {
            this.head = newNode;
        } else if (this.head.num >= newNode.num) {
            newNode.next = this.head;
            this.head = newNode;
        } else {
            intNode runner = this.head;
            while (runner.next != null &&
                    runner.next.num <= num)
                runner = runner.next;
            newNode.next = runner.next;
            runner.next = newNode;
        }
    }

    public void removeNode(int pos) {
        if (getSize() > pos)
            if (pos == 0) {
                this.head = this.head.next;
            } else {
                intNode runner = this.head;
                while (pos != 1) {
                    pos--;
                    runner = runner.next;
                }
                runner.next = runner.next.next;
            }
    }
}
