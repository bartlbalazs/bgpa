import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgxChartsModule } from '@swimlane/ngx-charts';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserStatsComponent } from './user-stats/user-stats.component';
import { ApiService } from './api.service';
import { GameListComponent } from './game-list/game-list.component';
import { PageNavComponent } from './page-nav/page-nav.component';
import { PreferencesComponent } from './preferences/preferences.component';
import { BadgesComponent } from './badges/badges.component';
import { SummaryComponent } from './summary/summary.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    UserStatsComponent,
    GameListComponent,
    PageNavComponent,
    PreferencesComponent,
    BadgesComponent,
    SummaryComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgxChartsModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
