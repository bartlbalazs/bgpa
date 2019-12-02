export const NATURAL_PALETTE: string[] = ['#bf9d76', '#e99450', '#d89f59', '#f2dfa7', '#a5d7c6', '#7794b1', '#afafaf', '#707160', '#ba9383', '#d9d5c3']

export const GAUGE_PALETTE: string[] = ['#bf9d76', '#e99450', '#d89f59']

export class ChartConfig {


    constructor(public view: any = [650, 650],
        public showXAxis: boolean = true, public showYAxis: boolean = true,
        public gradient: boolean = false, public showLegend: boolean = true,
        public xAxisLabel: string = 'Number', public showXAxisLabel: boolean = true,
        public yAxisLabel: string = 'Value', public showYAxisLabel: boolean = true,
        public timeline: boolean = true,
        public colorScheme: any = { domain: NATURAL_PALETTE },
        public showLabels: boolean = true, public explodeSlices: boolean = false, public doughnut: boolean = false) {
    }
}

export class LinearGaugeConfig {
    constructor(public scheme: any = { domain: GAUGE_PALETTE },
        public view: any = [] = [280, 150]) {
    }
}