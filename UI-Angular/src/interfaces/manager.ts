import { v4 as uuidv4 } from 'uuid';
import { PlayerStatus } from "../app/enums/PlayerStatus";


export interface Manager {
    id: string;
    contractID: string,
    fullName: string;
    dateOfBirth: string; // Consider using a Date type if possible
    age: number;
    nationality: string;
    role: string;
  }