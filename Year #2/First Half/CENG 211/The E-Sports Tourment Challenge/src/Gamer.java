/**
 * Represents a gamer participating in the E-Sports tournament.
 * Stores personal information and experience level of the gamer.
 */
public class Gamer {
    private int id;
    private String nickname;
    private String name;
    private String phone;
    private int experienceYears;

    /**
     * Constructor to create a Gamer object.
     * 
     * @param id The unique identifier for the gamer
     * @param nickname The gamer's nickname
     * @param name The gamer's real name
     * @param phone The gamer's phone number
     * @param experienceYears Years of gaming experience
     * @throws IllegalArgumentException if validation fails
     */
    public Gamer(int id, String nickname, String name, String phone, int experienceYears) {
        if (id <= 0) {
            throw new IllegalArgumentException("Gamer ID must be positive");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative");
        }
        
        this.id = id;
        this.nickname = nickname.trim();
        this.name = name.trim();
        this.phone = phone.trim();
        this.experienceYears = experienceYears;
    }

    /**
     * Copy constructor to create a Gamer object from another Gamer.
     * 
     * @param other The Gamer object to copy
     * @throws IllegalArgumentException if other is null
     */
    public Gamer(Gamer other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from null Gamer object");
        }
        
        this.id = other.id;
        this.nickname = other.nickname;
        this.name = other.name;
        this.phone = other.phone;
        this.experienceYears = other.experienceYears;
    }

    /**
     * Gets the gamer's nickname.
     * 
     * @return The nickname
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Gets the gamer's real name.
     * 
     * @return The real name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the gamer's years of experience.
     * 
     * @return Years of experience
     */
    public int getExperienceYears() {
        return experienceYears;
    }

    /**
     * Returns a string representation of the gamer.
     * 
     * @return String containing gamer details
     */
    @Override
    public String toString() {
        return "Gamer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }

    /**
     * Checks if two Gamer objects are equal based on their attributes.
     * 
     * @param obj The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Gamer gamer = (Gamer) obj;
        return id == gamer.id &&
               experienceYears == gamer.experienceYears &&
               nickname.equals(gamer.nickname) &&
               name.equals(gamer.name) &&
               phone.equals(gamer.phone);
    }
}

