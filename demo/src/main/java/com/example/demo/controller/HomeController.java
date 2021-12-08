package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.LoginRequest;
import com.example.demo.LoginResponse;
import com.example.demo.game.gameConcateWord.GameCWPlaying;
import com.example.demo.game.gameConcateWord.PlayerCW;
import com.example.demo.help.Response;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.Game;
import com.example.demo.model.GamePlay;
import com.example.demo.model.User;
import com.example.demo.repository.ChatReposity;
import com.example.demo.repository.DictionaryRepo;
import com.example.demo.repository.GamePlayReposity;
import com.example.demo.repository.GameReposity;
import com.example.demo.repository.NewsRespository;
import com.example.demo.repository.UserRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailSenderService;
import com.example.demo.service.UserService;
import com.example.demo.model.Mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GameReposity gameReposity;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private GamePlayReposity gamePlayReposity;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DictionaryRepo dictionaryRepo;
    @Autowired
    private ChatReposity chatReposity;
    @Autowired
    private NewsRespository newsRespository;
    @Value("${Server}")
    String server;
    @Value("${Client}")
    String client;

    private final int ERROR = 14;
    private final int SUCCESS = 15;
    private ArrayList<Mail>emails = new ArrayList<>();
    
    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.getRole().compareTo("ROLE_GEST")==0)return new LoginResponse();
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        Date now = new Date();
        long time = now.getTime()/1000;
        userRepository.updateLastLogin(time, loginRequest.getEmail());
        System.out.println(jwt);
        return new LoginResponse(jwt);
    }

    @GetMapping("/verify")
    public String verify(@RequestAttribute int id){
        System.out.println(id);
        User user = userRepository.getById(id);
        if(user==null)return "FUCK";
        userRepository.updateRole("ROLE_USER", id);
        String link =client+"/login";
        return "Click here to login :"+link;
    }
    private boolean checkEmail(String email){
        int n =email.length();
        if(n<=10)return false;
        String sub = email.substring(n-10, n);
        if(sub.compareTo("@gmail.com")!=0)return false;
        return true;

    }
    @PostMapping("/register")
    public Response signUp(@RequestBody User user) {
        if(user.getFirst_name()==null)return new Response(ERROR,"Thiếu first name");
        if(user.getLast_name()==null)return new Response(ERROR,"Thiếu last name");
        if(user.getEmail()==null)return new Response(ERROR,"Thiếu email");
        if(user.getPassword()==null)return new Response(ERROR,"Thiếu password");
        if(!checkEmail(user.getEmail()))return new Response(ERROR,"Sai email!!");
        System.out.println(user);
        User user_exist = userRepository.findByEmail(user.getEmail());
        if(user_exist != null)return new Response(ERROR, "Email đã tồn tại");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar("https://avatars.dicebear.com/api/micah/"+user.getFirst_name()  + user.getLast_name()+".svg");
        User new_user = userRepository.save(user);
        String jwt = tokenProvider.generateToken(new_user);
        String link =server+"/verify?token="+jwt;
       try {
        emailSenderService.sendSimpleEmail(user.getEmail(), "Click here to verify : "+link, "Verify");
       } catch (Exception e) {
           System.out.println(e);
           return new Response(ERROR, "Something wrong!!");
        }
       return new Response(SUCCESS, "Vào mail để xác thực!!!");
    }
    
    // @Scheduled(fixedDelay = 5000)
    // public void test(){
    //     // User user = userRepository.findById(33);
    // //     Game game = gameReposity.findById(1);
    // //     RecordMatch match = recordMatchReposity.findById(117);
    // //    GamePlay  play = gamePlayReposity.save(new GamePlay(user," ",game,match));
    // //  List<User> user = userRepo.findById(33);
    // //   User a = userRepository.getGame(33);
    // //   System.out.println(a.getGameplay_1());
    // // Random random = new Random();
    // // System.out.println((char)(random.nextInt(26)+'a'));
    // // PlayerCW player =new PlayerCW(user,10);
    // //  GameCWPlaying gameCWPlaying = new GameCWPlaying(1, player,player,player,player, 4);
    // //  gameCWPlaying.downTime();
    // // newsRespository.addNews(33, "AA","CC", "DD",0, 1, "??", 0, 0);
    // //    System.out.println( userRepository.numberUser());
    // // newsRespository.deleteComment(2);
    // // newsRespository.deleteNews(2);
    // }
}
