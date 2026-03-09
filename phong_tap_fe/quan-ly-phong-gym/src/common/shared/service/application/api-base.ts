// src/app/services/api-base.ts
export const API_BASE = {
  local: 'http://localhost:8080',
  prod: ' https://quan-ly-phong-gym.onrender.com/api',
};

export const IMAGE_BASE = {
  local: 'http://localhost:8080',
  prod: ' https://quan-ly-phong-gym.onrender.com/uploads/',
};

// Đổi môi trường ở đây
export const ENVIRONMENT: 'local' | 'prod' = 'local';

// Xuất ra biến dùng trong toàn app
export const API_CURRENT = API_BASE[ENVIRONMENT];
export const IMAGE_CURRENT = IMAGE_BASE[ENVIRONMENT];
