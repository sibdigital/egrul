webix.i18n.setLocale("ru-RU");

function view_section(title) {
    return {
        view: 'template',
        type: 'section',
        template: title
    }
}

function queueUpEGRUL() {
    webix.confirm('Вы действительно хотите поставить в расписание на запуск?')
        .then(
            function () {
                var loadVersionValue = $$('EGRULType').getValue();
                var timeEGRUL = $$('EGRULDate').getValue();
                params = {loadVersion: loadVersionValue, time: timeEGRUL}
                webix.ajax().get('/queue_up_egrul', params).then(function (data) {
                    if (data.text() === 'Ok') {
                        webix.message({
                            text: 'Расписание запуска ЕГРЮЛа установлено',
                            type: 'success'
                        });
                    } else {
                        webix.message({
                            text: 'Не удалось задать расписание запуска ЕГРЮЛа',
                            type: 'error'
                        });
                    }
                })
            }
        )
}

function queueUpEGRIP() {
    webix.confirm('Вы действительно хотите в расписание на запуск?')
        .then(
            function () {
                var loadVersionValue = $$('EGRIPType').getValue();
                var timeEGRIP = $$('EGRIPDate').getValue();
                params = {loadVersion: loadVersionValue, time: timeEGRIP}
                webix.ajax().get('/queue_up_egrip', params).then(function (data) {
                    if (data.text() === 'Ok') {
                        webix.message({
                            text: 'Расписание запуска ЕГРИПа установлено',
                            type: 'success'
                        });
                    } else {
                        webix.message({
                            text: 'Не удалось задать расписание запуска ЕГРИПа',
                            type: 'error'
                        });
                    }
                })
            }
        )
}

function deleteFromQueueEGRUL() {
    webix.confirm('Вы действительно хотите остановить/убрать из загрузки?')
        .then(
            function () {
                params = {type: 'egrul'}
                webix.ajax().get('/delete_queue', params).then(function (data) {
                    if (data.text() === 'Ok') {
                        webix.message({
                            text: 'Запуск по расписанию отменен',
                            type: 'success'
                        });
                    } else {
                        webix.message({
                            text: 'Не получилось отменить запуск',
                            type: 'error'
                        });
                    }
                })
            })
}

function deleteFromQueueEGRIP() {
    webix.confirm('Вы действительно хотите остановить/убрать из загрузки?')
        .then(
            function () {
                params = {type: 'egrip'}
                webix.ajax().get('/delete_queue', params).then(function (data) {
                    if (data.text() === 'Ok') {
                        webix.message({
                            text: 'Запуск по расписанию отменен',
                            type: 'success'
                        });
                    } else {
                        webix.message({
                            text: 'Не получилось отменить запуск',
                            type: 'error'
                        });
                    }
                })
            })
}


webix.ready(function() {
    webix.ui({
       container: 'app',
       view: "form",
       autowidth: true,
       height: document.body.clientHeight,
       width: document.body.clientWidth - 8,
       rows: [
            {
                id: 'formEGRUL',
                view: 'form',
                complexData: true,
                rows: [
                    view_section('ЕГРЮЛ'),
                    {view:"radio", id: "EGRULType", label:"Загрузка", value:1, options:[
                        { id:0, value:"Полная" },
                        { id:1, value:"Обновление" } // the initially selected item
                    ]},
                    {
                        view: "datepicker",
                        id: 'EGRULDate',
                        width: 300,
                        label: "Время",
                        timepicker: true
                    },
                    {
                        cols: [
                            {
                                view: 'button',
                                value: 'Поставить на выполнение',
                                align: 'left',
                                maxWidth: 300,
                                css: 'webix_primary',
                                click: queueUpEGRUL
                            },
                            {},
                            {
                                view: 'button',
                                value: 'Остановить загрузку ЕГРЮЛ',
                                align: 'right',
                                maxWidth: 400,
                                css: 'webix_danger',
                                click: deleteFromQueueEGRUL
                            },
                        ]
                    }
                ]
        },
        {
            id: 'formEGRIP',
            view: 'form',
            complexData: true,
            rows: [
                view_section('ЕГРИП'),
                {view:"radio", id: "EGRIPType", label:"Загрузка", value:1, options:[
                    { id:0, value:"Полная" },
                    { id:1, value:"Обновление" } // the initially selected item
                ]},
                {
                    view: "datepicker",
                    id: 'EGRIPDate',
                    width: 300,
                    label: "Время",
                    timepicker: true
                },
                {cols: [
                        {
                            view: 'button',
                            value: 'Поставить на выполнение',
                            align: 'left',
                            maxWidth: 300,
                            css: 'webix_primary',
                            click: queueUpEGRIP
                        },
                        {},
                        {
                            view: 'button',
                            value: 'Остановить загрузку ЕГРИП',
                            align: 'right',
                            maxWidth: 400,
                            css: 'webix_danger',
                            click: deleteFromQueueEGRIP
                        },
                    ]}
            ]
    }]
    })
})