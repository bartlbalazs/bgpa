import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ChartConfig, ChartType, BarChartConfig } from '../shared/ChartConfig';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.css']
})
export class PreferencesComponent implements OnChanges {
  constructor() { }

  @Input()
  displayName: String

  @Input()
  preferences: any

  @Input()
  chartConfig: ChartConfig = new ChartConfig()

  @Input()
  chartType: ChartType = ChartType.PIE

  ChartType = ChartType

  @Input()
  barChartConfig = new BarChartConfig()

  selected_items: any[]

  onSelectFromChart($event: any) {
    let id = $event.name || $event
    this.selected_items = this.preferences.fullData[id]
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.selected_items = null
  }
}
