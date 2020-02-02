package com.example.demo.accounts;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findByUserName() {
        //Given
        String password = "keesun";
        String username = "keesun@email.com";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                .build();
        this.accountRepository.save(account);

        //When
        UserDetailsService userDetailsService = (UserDetailsService)accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //Then
        assertThat(userDetails.getPassword()).isEqualTo(password);
    }

    //@Test(expected = UsernameNotFoundException.class) //예외 타입만 확인 가능
    @Test
    public void findByUsernameFail() {
        /*
        String username = "random@email.com";
        accountService.loadUserByUsername(username);
        */

        //복잡하지만 예외 타입, 메시지까지 확인 가능하다.
        /*
        try {
            accountService.loadUserByUsername(username);
            fail("supposed to be failed");
        } catch(UsernameNotFoundException e) {
            assertThat(e.getMessage()).containsSequence(username);
        }
         */

        //코드는 간결하면서 예외 타입과 메시지 모두 확인 가능
        //Expected
        String username = "random@email.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.containsString(username));

        //When
        accountService.loadUserByUsername(username);
    }
}