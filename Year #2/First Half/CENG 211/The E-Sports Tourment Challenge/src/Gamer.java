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
     */
    public Gamer(int id, String nickname, String name, String phone, int experienceYears) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.experienceYears = experienceYears;
    }

    /**
     * Copy constructor to create a new Gamer object as a copy of another.
     * 
     * @param other The Gamer object to copy from
     */
    public Gamer(Gamer other) {
        this.id = other.id;
        this.nickname = other.nickname;
        this.name = other.name;
        this.phone = other.phone;
        this.experienceYears = other.experienceYears;
    }

    /**
     * Gets the gamer ID.
     * 
     * @return The gamer's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the gamer ID.
     * 
     * @param id The unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
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
     * Sets the gamer's nickname.
     * 
     * @param nickname The nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
     * Sets the gamer's real name.
     * 
     * @param name The real name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the gamer's phone number.
     * 
     * @return The phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the gamer's phone number.
     * 
     * @param phone The phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * Sets the gamer's years of experience.
     * 
     * @param experienceYears Years of experience to set
     */
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
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

