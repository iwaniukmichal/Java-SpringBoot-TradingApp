INSERT INTO controllers_tests (endpoint_name, uri, expected_value, request_type, response_type)
VALUES
    ('integration', '/api/example', '', 'GET', 'internal_error'),

    ('basic', '/trading_data/basic', '{"error":"Required request parameter ''symbol'' for method parameter type String is not present"}', 'GET', 'internal_error'),
    ('basic', '/trading_data/basic?symbol=PolitechnikaWarszawska', '{"Symbol":"Symbol unknown."}', 'GET', 'error'),
    ('basic', '/trading_data/basic?symbol=ibm', '', 'GET', 'ok'),

    ('basic_chart', '/trading_data/basic/basic_chart', '{"error":"Required request parameter ''symbol'' for method parameter type String is not present"}', 'GET', 'internal_error'),
    ('basic_chart', '/trading_data/basic/basic_chart?symbol=ibm', '{"type":"Wrong type."}', 'GET', 'error'),
    ('basic_chart', '/trading_data/monthly/basic_chart?symbol=ibm', '', 'GET', 'ok'),
    ('basic_chart', '/trading_data/advanced/basic_chart?symbol=ibm&interval=30min&month=2009-01', '', 'GET', 'ok'),
    ('basic_chart', '/trading_data/advanced/basic_chart?symbol=ibm&interval=30min&month=1900-01', '', 'GET', 'error'),
    ('basic_chart', '/trading_data/advanced/basic_chart?symbol=ibm&interval=11min&month=2009-01', '', 'GET', 'error'),

    ('volume_chart', '/trading_data/basic/volume_chart', '{"error":"Required request parameter ''symbol'' for method parameter type String is not present"}', 'GET', 'internal_error'),
    ('volume_chart', '/trading_data/basic/volume_chart?symbol=ibm', '{"type":"Wrong type."}', 'GET', 'error'),
    ('volume_chart', '/trading_data/monthly/volume_chart?symbol=ibm', '', 'GET', 'ok'),
    ('volume_chart', '/trading_data/advanced/volume_chart?symbol=ibm&interval=30min&month=2009-01', '', 'GET', 'ok'),
    ('volume_chart', '/trading_data/advanced/volume_chart?symbol=ibm&interval=30min&month=1900-01', '', 'GET', 'error'),
    ('volume_chart', '/trading_data/advanced/volume_chart?symbol=ibm&interval=11min&month=2009-01', '', 'GET', 'error'),

    ('statistics', '/trading_data/basic/statistics', '{"error":"Required request parameter ''symbol'' for method parameter type String is not present"}', 'GET', 'internal_error'),
    ('statistics', '/trading_data/basic/statistics?symbol=ibm', '{"type":"Wrong type."}', 'GET', 'error'),
    ('statistics', '/trading_data/monthly/statistics?symbol=ibm', '', 'GET', 'ok'),
    ('statistics', '/trading_data/advanced/statistics?symbol=ibm&interval=30min&month=2009-01', '', 'GET', 'ok'),
    ('statistics', '/trading_data/advanced/statistics?symbol=ibm&interval=30min&month=1900-01', '', 'GET', 'error'),
    ('statistics', '/trading_data/advanced/statistics?symbol=ibm&interval=11min&month=2009-01', '', 'GET', 'error');
