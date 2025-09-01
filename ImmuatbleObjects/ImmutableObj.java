package ImmuatbleObjects;

public class ImmutableObj {
    private final String name;
    private final String email;
    private final int id;
    private final int[] marks;

    ImmutableObj(Builder b) {
        this.name = b.name;
        this.email = b.email;
        this.id = b.id;
        this.marks=b.marks.clone();
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

    public int[] getMarks() {
        return this.marks.clone();
        //if we directly return the marks, 
        //which returns a ref, nd can be modified by the client.
    }

    static class Builder {
        private String name;
        private String email;
        private int id;
        private int[] marks;

        public ImmutableObj build() {
            return new ImmutableObj(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }
    }
}
