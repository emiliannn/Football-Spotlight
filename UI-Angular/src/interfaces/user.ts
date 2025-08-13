// import { TaskStatus } from "../enums/enum";
import { v4 as uuidv4 } from 'uuid';
import { PlayerStatus } from "../app/enums/PlayerStatus";


export interface User {
  id: string,
  fullName: string,
  mode: string
  }