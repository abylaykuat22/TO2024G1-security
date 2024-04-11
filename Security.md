- `PasswordEncoder`: Интерфейс для шифрования паролей.
- `UserDetailsService`: Интерфейс для загрузки пользовательских данных.
- `SecurityFilterChain`: Фильтры безопасности HTTP запросов.
- `AuthenticationManagerBuilder`: Настройка аутентификации.
- `formLogin`: Конфигурация формы входа.
- `.loginProcessingUrl`: URL для обработки запроса на аутентификацию.
- `.defaultSuccessUrl`: URL после успешной аутентификации.
- `.failureUrl`: URL после неудачной аутентификации.
- `.usernameParameter`: Параметр с именем пользователя в запросе.
- `.passwordParameter`: Параметр с паролем пользователя в запросе.
- `csrf(AbstractHttpConfigurer::disable)`: Отключение CSRF защиты.
- `UserDetails`: Интерфейс, содержащий информацию о пользователе для аутентификации и авторизации.
- `GrantedAuthority`: Интерфейс, представляющий роль или разрешение пользователя.
- `exceptionHandling().accessDeniedPage("/forbidden")`: Устанавливает страницу для отображения при отказе в доступе.

Пример конфигурации:
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(
        AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());

    httpSecurity.formLogin(fl -> fl
        .loginProcessingUrl("/auth")
        .defaultSuccessUrl("/", true)
        .failureUrl("/signin?error")
        .usernameParameter("username")
        .passwordParameter("password")
    );


    httpSecurity.logout(logount -> logount
        .logoutUrl("/signout")
        .logoutSuccessUrl("/signin"));

    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    return httpSecurity.build();
  }
}
```