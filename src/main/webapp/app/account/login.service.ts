import { ref } from 'vue';

export const loginModalVisible = ref(false);

export function showLoginModal(): void {
  loginModalVisible.value = true;
}

export function hideLoginModal(): void {
  loginModalVisible.value = false;
}

export default class LoginService {
  public openLogin(_instance?: unknown): void {
    showLoginModal();
  }
}
