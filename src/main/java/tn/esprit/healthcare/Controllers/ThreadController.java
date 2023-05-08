package tn.esprit.healthcare.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tn.esprit.healthcare.Entities.Answer;
import tn.esprit.healthcare.Entities.Thread;
import tn.esprit.healthcare.Entities.Topic;
import tn.esprit.healthcare.Entities.User;
import tn.esprit.healthcare.Repositories.UserRepository;
import tn.esprit.healthcare.Services.IThreadService;
import tn.esprit.healthcare.Services.UserDetailsImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/thread-op")

public class ThreadController {

    @Autowired
    IThreadService threadService ;

    @Autowired
    UserRepository userRepository ;


    /*@PostMapping("/add-thread")
    public ResponseEntity<Void> addThread (@RequestBody Thread thread) {
        threadService.addThread(thread);
        return ResponseEntity.status(HttpStatus.CREATED).build() ;
    }*/

   /* @PostMapping("/add-thread")
    public Thread addThread (@RequestBody Thread thread,Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).get();
        thread.setUser(user);
        return threadService.addThread(thread) ;
    }*/
    @PostMapping("/add-thread")
    public Thread addThread (@RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("title") String title,
                             @RequestParam("topic") Topic topic ,
                             @RequestParam("question") String question,
                             @RequestParam("userId") Long userId
                             ) throws IOException{
        Thread thread = new Thread();
        User user = userRepository.findById(userId).get();
        if (file != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "thread-images/";
            String filePath = uploadDir + fileName;
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            thread.setCoverPhotoThread(filePath);
        }
        thread.setUser(user);
        thread.setTitleThread(title);
        thread.setQuestionThread(question);
        thread.setTopicThread(topic);
        return threadService.addThread(thread);
    }




    @DeleteMapping("/delete-thread/{id}")
    public ResponseEntity<String> deleteThread (@PathVariable long id) {
        Thread thread = threadService.findThreadById(id) ;
        threadService.deleteThread(thread);
        return ResponseEntity.ok("Thread "+id+" Deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thread> getThreadById (@PathVariable long id) {
        Thread thread = threadService.findThreadById(id) ;
        return ResponseEntity.ok(thread) ;
    }

    @GetMapping
    public ResponseEntity<Iterable<Thread>> getThreads() throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();
        try {
            Iterable<Thread> threads = threadService.findAll();
            return ResponseEntity.ok(threads);
        } catch (Exception e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-active-threads")
    public ResponseEntity<List<Thread>> getActiveThreads() {
        List<Thread> activeThreads = threadService.getActiveStatus();
        return ResponseEntity.ok(activeThreads);
    }

    @PutMapping("/update-thread/{idThread}")
    public ResponseEntity<Void> updateThread (@RequestBody Thread updatedThread, @PathVariable Long idThread) {
        // Set the id of the updated answer to the id in the path variable
        updatedThread.setIdThread(idThread);
        // Call the service method to update the answer
        threadService.updateThread(updatedThread);
        // Return a response with no content and a status of OK
        return ResponseEntity.ok().build();
    }


    @GetMapping("/thread-byTopic/{topic}")
    public Set<Thread> getThreadByTopic (@PathVariable Topic topic) {
        return threadService.findThreadByTopic(topic) ;
    }

    @GetMapping("thread-byKeyWord/{keyword}")
    public Set<Thread> getThreadByKeyWord (@PathVariable String keyword) {
        return threadService.findThreadByKeyWord(keyword) ;
    }



    @PostMapping("up/{id}")
    public ResponseEntity<Void> upThread (@PathVariable long id) {
        threadService.upThread(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("down/{id}")
    public ResponseEntity<Void> downThread (@PathVariable long id) {
        threadService.downThread(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topics")
    public List<String> getTopics () {
        return threadService.getTopics();
    }

    @GetMapping("/threads-ByVotes")
    public ResponseEntity<List<Thread>> getThreadsSortedByVotes () {
        List<Thread> threads = threadService.getThreadsSortedByVotes() ;
        return ResponseEntity.ok(threads) ;
    }

    /*@GetMapping("/timeframe/{timeFrame}")
    public ResponseEntity<List<Thread>> getThreadsByTimeFrame(@PathVariable String timeFrame) {
        try {
            List<Thread> threads = threadService.getThreadsByTimeFrame(timeFrame);
            return ResponseEntity.ok(threads);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }*/

    @GetMapping("/count-by-month/{year}")
    public Map<String,Integer> getThreadCountByMonthInYear (@PathVariable int year) {
        return threadService.getThreadCountsByMonthInYear(year) ;
    }


    private static class FileUploadUtil {
        public static void saveFile(String uploadDir, String fileName,
                                    MultipartFile multipartFile) throws IOException {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                Path filePath = uploadPath.resolve(fileName);
                multipartFile.transferTo(filePath);
            } catch (IOException e) {
                throw new IOException("Could not save file: " + fileName, e);
            }
        }
    }


}
