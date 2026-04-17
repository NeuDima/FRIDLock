package neupokoev.com.lockservice.dto;

public record LockCreateDto(String name, String secret, Integer groupId) {
}
