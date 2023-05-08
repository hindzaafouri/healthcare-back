package tn.esprit.healthcare.Services;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.healthcare.Entities.PatientDto;
import tn.esprit.healthcare.Entities.PatientFile;
import tn.esprit.healthcare.Repositories.PatientFileRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class IPatientFileServiceImpl implements IPatientFileService {
    @Autowired
    PatientFileRepository patientFileRepository;
    EmailService emailService;
    @Override
    public List<PatientFile> getAllPatientFile() {
        return patientFileRepository.findAll();

    }



    @Override
    public PatientFile addPatientFile(PatientFile u, HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontHeader.setSize(22);

        Paragraph headerParagraph = new Paragraph(u.getUsername(), fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.TIMES);
        fontParagraph.setSize(14);

        Paragraph pdfParagraph = new Paragraph(u.getContent(), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pdfParagraph2 = new Paragraph(String.valueOf(u.getConsutation_nbr()), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pdfParagraph3 = new Paragraph(u.getState(), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);

        // DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        //    pdfParagraph.add("Row: "
        //      +" Time: "+dateFormatter.format(new Date()) +"\n");


        document.add(headerParagraph);
        document.add(pdfParagraph);
        document.add(pdfParagraph2);
        document.add(pdfParagraph3);
        document.close();

        return patientFileRepository.save(u);
    }

    @Override
    public void removePatientFile(Long id) {
        patientFileRepository.deleteById(id);

    }

    @Override
    public Optional<PatientFile> getPatientFile(Long id) {
        return patientFileRepository.findById(id);
    }

    @Override
    public List<PatientFile> findByStatus(String status) {

        return patientFileRepository.findByStatusStartsWith(status);
    }

    @Override
    public PatientFile updatePatientFile(PatientFile u, HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontHeader.setSize(22);

        Paragraph headerParagraph = new Paragraph(u.getUsername(), fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.TIMES);
        fontParagraph.setSize(14);

        Paragraph pdfParagraph = new Paragraph(u.getContent(), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pdfParagraph2 = new Paragraph(String.valueOf(u.getConsutation_nbr()), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pdfParagraph3 = new Paragraph(u.getState(), fontParagraph);
        pdfParagraph.setAlignment(Paragraph.ALIGN_LEFT);

        // DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
        //    pdfParagraph.add("Row: "
        //      +" Time: "+dateFormatter.format(new Date()) +"\n");


        document.add(headerParagraph);
        document.add(pdfParagraph);
        document.add(pdfParagraph2);
        document.add(pdfParagraph3);
        document.close();

        return patientFileRepository.save(u);    }

    @Override
    public int nombreselonstatus(String state) {
        return 0;
    }

    public PatientDto nombreselonstatusDto() {
        PatientDto patientDto = new PatientDto();
        patientDto.setMalade(patientFileRepository.nombredepatients("malade"));
        patientDto.setGueri(patientFileRepository.nombredepatients("gueri"));
        return patientDto;
    }
}

