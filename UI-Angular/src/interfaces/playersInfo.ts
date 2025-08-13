// import { TaskStatus } from "../enums/enum";
import { v4 as uuidv4 } from 'uuid';
import { PlayerStatus } from "../app/enums/PlayerStatus";


export interface PlayerInfo {
    id: string;
    fullName: string;
    contractID: string;
    dateOfBirth: string;
    age: number;
    height: number;
    citizenship: string;
    position: string;
    foot: string;
    outfitter: string;
    currentClub: string;
    previousClub: string;
    manager: string;
  }