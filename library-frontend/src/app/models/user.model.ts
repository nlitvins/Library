export interface User {
  id: number;
  name: string;
  secondName: string;
  userName: string;
  email: string;
  mobileNumber: string;
  personCode: string;
  role: string;
}

export interface CreateUserRequest {
  name: string;
  secondName: string;
  userName: string;
  email: string;
  mobileNumber: string;
  personCode: string;
} 