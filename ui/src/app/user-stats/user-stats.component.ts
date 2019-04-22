import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiService } from '../api.service';
import { Subscription } from 'rxjs';
import { ChartConfig } from '../shared/ChartConfig'
import { PopularityData } from '../shared/PopularityData'

@Component({
  selector: 'app-user-stats',
  templateUrl: './user-stats.component.html',
  styleUrls: ['./user-stats.component.css']
})
export class UserStatsComponent implements OnInit, OnDestroy {

  chartConfig: ChartConfig

  statSubscription: Subscription;

  userId: String

  themePopularities: PopularityData
  mechanismPopularities: PopularityData

  selected_theme: any
  selected_mechanism: any

  constructor(public apiService: ApiService) { }

  ngOnInit() {
    this.statSubscription = this.apiService.userStats.subscribe(this.handleUserStats())
    this.chartConfig = new ChartConfig()
  }

  private handleUserStats(): (value: any) => void {
    return message => {
      if (message.userId) {
        this.userId = message.userId;
        this.themePopularities = PopularityData.fromRawData(message.categoryPopularities);
        this.mechanismPopularities = PopularityData.fromRawData(message.mechanismPopularities);
        this.selected_theme = null
        this.selected_mechanism = null
      }
    };
  }

  ngOnDestroy(): void {
    this.statSubscription.unsubscribe();
  }

  onSelectFromChart(chartId: string, $event: any) {
    this['selected_' + chartId] = this[chartId + 'Popularities'].fullData[$event.name]
  }
}
