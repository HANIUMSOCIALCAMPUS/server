package hanium.social_campus.service.email;

import hanium.social_campus.domain.email.VerifyCode;
import hanium.social_campus.repository.email.VerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerifyCodeService {

    private final VerifyCodeRepository verifyCodeRepository;

    private final EmailSenderService emailSenderService;

    public void createVerifyCode(String receiverEmail) {
        String code = createKey();
        VerifyCode verifyCode = VerifyCode.createCode(code);
        verifyCodeRepository.save(verifyCode);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject("[혼밥메이트] 대학교 이메일 인증");
        simpleMailMessage.setText("["+code+"]인증번호를 입력해주세요.");
        emailSenderService.sendEmail(simpleMailMessage);
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public Optional<VerifyCode> findExpiredCode(String code) {
        return verifyCodeRepository.find(code, false);
    }

}
