import { v4 as uuidv4 } from 'uuid';
import { PlayerStatus } from "../app/enums/PlayerStatus";


export interface Contract {
  id: string;
  contractExpires:string;
  contractOption:string;
  currentClubID:string;
  joined: string;
  previousClubID: string;
}