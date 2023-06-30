export class User {
  id!: number;
  fullName!: string;
  email!: string;
  password!: string;
  contact!: string;
  gender!: string;
  dateOfBirth!: Date;
  pdfFile: File | null = null;
}
