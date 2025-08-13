import { v4 as uuidv4 } from 'uuid';
import { PlayerStatus } from "../app/enums/PlayerStatus";


export interface FootballClub {
  id: string;
  name: string;
  squadSize: string; // Consider using a Date type if possible
  averageAge: number;
  stadium: string;
  managerID: string;
}

// const footballClub: FootballClub = {
//   id: uuidv4(),
//   name: "Example Club",
//   squadSize: "20",
//   averageAge: 25,
//   stadium: "Example Stadium",
//   managerID: "12345"
// };
