import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContractComponent } from './components/contract/contract.component';
import { ManagerComponent } from './components/manager/manager.component';
import { PlayerComponent } from './components/player/player.component';
import { MatToolbarModule  } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatTabsModule } from '@angular/material/tabs';
import {MatSelectModule} from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { DragScrollComponent  } from 'ngx-drag-scroll';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PlayerDetailsComponent } from './components/player-details/player-details.component';
import { ManagerDetailsComponent } from './components/manager-details/manager-details.component';
import { MatFormFieldModule  } from '@angular/material/form-field';
import { MatButtonToggleModule }    from '@angular/material/button-toggle';
import { AddItemComponent } from './components/add-item/add-item.component';
import { PlayerService } from './services/player.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ContractComponent,
    ManagerComponent,
    PlayerComponent,
    PlayerDetailsComponent,
    ManagerDetailsComponent,
    AddItemComponent,
  ],
  imports: [
    HttpClientModule,
    DragScrollComponent,
    NgbModule,
    BrowserModule,
    MatSelectModule,
    AppRoutingModule,
    MatToolbarModule,
    MatMenuModule,
    MatRadioModule,
    MatProgressSpinnerModule,
    ReactiveFormsModule, FormsModule,
    MatIconModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatFormFieldModule,
    MatButtonToggleModule
  ],
  providers: [PlayerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
