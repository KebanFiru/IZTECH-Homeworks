package types;

import model.Applicant;

/**
 * Bir burs başvurusunun temelini temsil eden soyut (abstract) sınıf.
 * "Composition" ilkesini kullanarak bir 'Applicant' nesnesi içerir ve
 * bu veriyi alt sınıfların (Merit, Need, Research) kullanması için
 * ortak bir çatı sağlar.
 * * Bu versiyonda değerlendirme mantığı veya sonuç alanları bulunmamaktadır.
 */
public abstract class Application {

    protected Applicant applicant;

    public Application(Applicant applicant) {
        if (applicant == null) {
            throw new IllegalArgumentException("Applicant cannot be null.");
        }

        this.applicant = new Applicant(applicant);
    }

    public Applicant getApplicant() {
        return new Applicant(applicant);
    }

    public abstract String getScholarshipCategory();

    @Override
    public String toString() {
        return String.format("Application: " + applicant.toString());
    }


}