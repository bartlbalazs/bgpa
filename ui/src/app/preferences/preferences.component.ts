import { Component, Input, OnChanges, SimpleChanges, OnInit } from '@angular/core';
import { ChartConfig, ChartType, BarChartConfig, BubbleChartConfig } from '../shared/ChartConfig';

@Component({
  selector: 'app-preferences',
  templateUrl: './preferences.component.html',
  styleUrls: ['./preferences.component.css']
})
export class PreferencesComponent implements OnInit, OnChanges {

  constructor() { }

  @Input()
  displayName: String

  @Input()
  filterName: String

  @Input()
  preferences: any

  @Input()
  filteredPreferences: any

  selectedDataset: any

  @Input()
  chartConfig: ChartConfig = new ChartConfig()

  @Input()
  chartType: ChartType = ChartType.PIE

  ChartType = ChartType

  @Input()
  barChartConfig = new BarChartConfig()

  @Input()
  bubbleChartConfig = new BubbleChartConfig()

  filteringActive: boolean = false

  selected_items: any[]

  selected_item: any

  onSelectFromChart($event: any) {
    let id = $event.name || $event
    this.selected_items = this.selectedDataset.fullData[id]
  }

  onSelectItem($event: any) {
    this.selected_item= $event
  }

  onFiltering() {
    this.filteringActive = !this.filteringActive;
    this.ngOnChanges(null);
  }

  ngOnInit(): void {
    this.selectedDataset = this.preferences;
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.selected_item = null;
    this.selected_items = null;
    this.selectedDataset = this.filteringActive ? this.filteredPreferences : this.preferences;
  }
}
