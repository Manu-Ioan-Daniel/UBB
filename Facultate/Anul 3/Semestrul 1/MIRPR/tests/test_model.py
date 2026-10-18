import unittest

from app import EXAMPLE, predict


class PredictionTests(unittest.TestCase):
    def test_empty_input_returns_fixed_prediction(self):
        self.assertEqual(predict(""), predict(EXAMPLE))

    def test_prediction_is_fixed(self):
        self.assertEqual(predict(EXAMPLE), predict("Alt sistem"))

    def test_all_three_views_are_present(self):
        result = predict(EXAMPLE)
        for view in ('systemContext shop "Context"', 'container shop "Containers"', 'component api "Components"'):
            self.assertIn(view, result)


if __name__ == "__main__":
    unittest.main()
